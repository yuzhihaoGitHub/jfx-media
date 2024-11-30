package com.yuzhihao.learn.config;

import com.yuzhihao.learn.VlcjApplication;
import lombok.extern.log4j.Log4j2;
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.factory.discovery.provider.DiscoveryDirectoryProvider;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 基于打包后获取VLC的lib目录
 *
 * @author yuzhihao
 */
@Log4j2
public class MyOsProvider implements DiscoveryDirectoryProvider {

    private final TempFile tempFile = new TempFile("lib");


    @Override
    public int priority() {
        return 0;
    }


    @Override
    public String[] directories() {
        boolean packaged = Objects.nonNull(System.getProperty("is.packaged"));
        if (packaged) {
            createTempFile();
            log.info("通过 springboot resources 流 读取 VLC LIB：{}", tempFile.temParentPath);
            return new String[]{tempFile.temParentPath};
        }
        try {
            String path = VlcjApplication.context.getResource("/vlc/macos/lib").getFile().getAbsolutePath();
            log.info("通过 springboot resources 目录读取 VLC LIB：{}", path);
            return new String[]{path};
        } catch (Exception e) {
            log.error("springboot resources 读取VLC LIB 失败：{}", e.getMessage(), e);
        }
        return new String[]{"./lib"};
    }

    private final class TempFile {

        private String parentPath;
        private String temParentPath;

        public TempFile(String tempDir) {
            try {
                File path = File.createTempFile(UUID.randomUUID().toString(), ".suffix");
                this.parentPath = path.getParent() + "/vlc/";
                this.temParentPath = parentPath + tempDir;
                path.delete();
            } catch (Exception e) {
                boolean packaged = Objects.nonNull(System.getProperty("is.packaged"));
                if (packaged) {
                    if (RuntimeUtil.isMac()) {
                        parentPath = Paths.get(System.getProperty("user.home"), "Library", "Application Support", "VLCJ", "temp").toString();
                    } else if (RuntimeUtil.isWindows()) {
                        parentPath = Paths.get(System.getenv("APPDATA"), "VLCJ", "temp").toString();
                    }
                }
            }
            log.info("临时文件目录：{}", temParentPath);
        }

        public void createTempFile(String[] names, String tempDir) {
            for (String name : names) {
                createTempFile(name, tempDir);
            }
        }

        private File createTempFile(String springSourceFileName, String tempDir) {
            try {
                String[] split = springSourceFileName.split("/");
                String tempFileName = split[split.length - 1];

                //要创建的临时文件目录
                Path tempParent = Paths.get(parentPath + tempDir);
                Path tempFile = Paths.get(parentPath + tempDir + "/" + tempFileName);
                if (tempFile.toFile().exists()) {
                    log.info("已存在临时文件：{}", tempFile);
                    return null;
                }

                InputStream stream = Objects.requireNonNull(getClass().getResourceAsStream(springSourceFileName));

                File dirFile = File.createTempFile(tempFileName, null, Files.createDirectories(tempParent).toFile());

                Files.move(dirFile.toPath(), tempFile, StandardCopyOption.REPLACE_EXISTING);

                log.debug("临时文件：{}", tempFile);
                copy(stream, tempFile.toFile());

                return tempFile.toFile();
            } catch (Exception e) {
                log.error("临时文件创建失败：{}", e.getMessage());
            }
            return null;
        }

    }

    private File createTempFile(InputStream stream, String prefix, String suffix) throws IOException {
        File path = File.createTempFile(UUID.randomUUID().toString(), suffix);

        File tempFile = File.createTempFile(prefix, suffix, Files.createDirectories(Paths.get(path.getParent() + "/vlc")).toFile());

        Path target = Paths.get(tempFile.getParent() + "/" + prefix + suffix);
        File file = target.toFile();
        file.deleteOnExit();

        Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        log.info("临时文件目录：{}", file.toPath());

        copy(stream, file);

        tempFile.deleteOnExit();
        path.delete();
        return tempFile;
    }

    @Override
    public boolean supported() {
        return true;
    }

    private static void copy(InputStream stream, File file) {
        // 使用 try-with-resources 确保流会被关闭
        try (OutputStream outputStream = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024]; // 创建一个缓冲区
            int bytesRead;

            // 循环读取数据并写入到目标文件
            while ((bytesRead = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead); // 将读取到的字节写入目标文件
            }

        } catch (IOException e) {
            log.error("拷贝文件时出错: " + e.getMessage(), e);
        }
    }

    private final static String[] LIBS = {
            "libvlc.5.dylib",
            "libvlc.dylib",
            "libvlccore.9.dylib",
            "libvlccore.dylib"
    };

    private final static String[] PLUGINS = {
            "libtospdif_plugin.dylib", "libddummy_plugin.dylib", "libtaglib_plugin.dylib", "liblibass_plugin.dylib", "libspeex_plugin.dylib", "libi420_rgb_plugin.dylib", "libmarq_plugin.dylib", "libugly_resampler_plugin.dylib", "librawvideo_plugin.dylib", "libfreetype_plugin.dylib", "libscreen_plugin.dylib", "libadummy_plugin.dylib", "libmosaic_plugin.dylib", "libaccess_concat_plugin.dylib", "libasf_plugin.dylib", "libpng_plugin.dylib", "libaom_plugin.dylib", "libstream_out_autodel_plugin.dylib", "libpsychedelic_plugin.dylib", "libdolby_surround_decoder_plugin.dylib", "libequalizer_plugin.dylib", "libmux_ps_plugin.dylib", "libi420_10_p010_plugin.dylib", "libaes3_plugin.dylib", "libnormvol_plugin.dylib", "libstream_out_dummy_plugin.dylib", "libscaletempo_pitch_plugin.dylib", "libinteger_mixer_plugin.dylib", "libbluray-awt-j2se-1.3.2.jar", "libxa_plugin.dylib", "libaccess_srt_plugin.dylib", "libi420_yuy2_plugin.dylib", "libvideotoolbox_plugin.dylib", "libcroppadd_plugin.dylib", "libvoc_plugin.dylib", "libscte27_plugin.dylib", "libnoseek_plugin.dylib", "libaccess_output_file_plugin.dylib", "libvout_macosx_plugin.dylib", "libstream_out_gather_plugin.dylib", "libflaschen_plugin.dylib", "libmux_ts_plugin.dylib", "libavcodec_plugin.dylib", "libedgedetection_plugin.dylib", "libaddonsvorepository_plugin.dylib", "libcaopengllayer_plugin.dylib", "libedummy_plugin.dylib", "libbonjour_plugin.dylib", "libpacketizer_av1_plugin.dylib", "libcache_read_plugin.dylib", "libblendbench_plugin.dylib", "libinvert_plugin.dylib", "libvc1_plugin.dylib", "libdcp_plugin.dylib", "libfolder_plugin.dylib", "libstream_out_record_plugin.dylib", "libpacketizer_mlp_plugin.dylib", "libavcapture_plugin.dylib", "libvdr_plugin.dylib", "libfaad_plugin.dylib", "libdecomp_plugin.dylib", "liba52_plugin.dylib", "libvobsub_plugin.dylib", "libsecuretransport_plugin.dylib", "libfreeze_plugin.dylib", "libpacketizer_copy_plugin.dylib", "libscene_plugin.dylib", "libupnp_plugin.dylib", "libposterize_plugin.dylib", "libsubsdec_plugin.dylib", "libgestures_plugin.dylib", "libgnutls_plugin.dylib", "libstream_out_transcode_plugin.dylib", "libimage_plugin.dylib", "libvmem_plugin.dylib", "libstream_out_stats_plugin.dylib", "libsatip_plugin.dylib", "libaccess_output_dummy_plugin.dylib", "libwav_plugin.dylib", "libsamplerate_plugin.dylib", "liblpcm_plugin.dylib", "libchorus_flanger_plugin.dylib", "libmux_mpjpeg_plugin.dylib", "libhotkeys_plugin.dylib", "libdvdnav_plugin.dylib", "libhqdn3d_plugin.dylib", "libmirror_plugin.dylib", "libflac_plugin.dylib", "libcaf_plugin.dylib", "plugins.dat", "libaudio_format_plugin.dylib", "libsmf_plugin.dylib", "libspatializer_plugin.dylib", "libanaglyph_plugin.dylib", "libsap_plugin.dylib", "libmono_plugin.dylib", "libx26410b_plugin.dylib", "libmediadirs_plugin.dylib", "libnuv_plugin.dylib", "libalphamask_plugin.dylib", "libstereo_widen_plugin.dylib", "libtwolame_plugin.dylib", "libsepia_plugin.dylib", "libaccess_output_udp_plugin.dylib", "libmux_mp4_plugin.dylib", "libaudiobargraph_a_plugin.dylib", "librawdv_plugin.dylib", "libstream_out_description_plugin.dylib", "libglconv_cvpx_plugin.dylib", "libt140_plugin.dylib", "libmux_ogg_plugin.dylib", "libstream_out_setid_plugin.dylib", "libstream_out_cycle_plugin.dylib", "libtextst_plugin.dylib", "libi422_yuy2_plugin.dylib", "libmotiondetect_plugin.dylib", "libaccess_output_livehttp_plugin.dylib", "libwave_plugin.dylib", "libpacketizer_dirac_plugin.dylib", "libfps_plugin.dylib", "libwebvtt_plugin.dylib", "libhttps_plugin.dylib", "libball_plugin.dylib", "libpva_plugin.dylib", "libvisual_plugin.dylib", "liblogo_plugin.dylib", "libdiracsys_plugin.dylib", "libfingerprinter_plugin.dylib", "libcvdsub_plugin.dylib", "libsharpen_plugin.dylib", "libfile_keystore_plugin.dylib", "libclone_plugin.dylib", "libgaussianblur_plugin.dylib", "libavi_plugin.dylib", "librtp_plugin.dylib", "libmemory_keystore_plugin.dylib", "libyuy2_i420_plugin.dylib", "libstream_out_chromaprint_plugin.dylib", "libsubsusf_plugin.dylib", "libdvbsub_plugin.dylib", "libkaraoke_plugin.dylib", "libmotionblur_plugin.dylib", "libpacketizer_flac_plugin.dylib", "libripple_plugin.dylib", "libpacketizer_h264_plugin.dylib", "libwall_plugin.dylib", "libsftp_plugin.dylib", "libcanvas_plugin.dylib", "librawvid_plugin.dylib", "libstream_out_display_plugin.dylib", "libaccess_imem_plugin.dylib", "libblend_plugin.dylib", "libvod_rtsp_plugin.dylib", "libi420_nv12_plugin.dylib", "libauhal_plugin.dylib", "libpuzzle_plugin.dylib", "libstream_out_delay_plugin.dylib", "libattachment_plugin.dylib", "libnfs_plugin.dylib", "libgme_plugin.dylib", "libexport_plugin.dylib", "libskiptags_plugin.dylib", "libantiflicker_plugin.dylib", "liblogger_plugin.dylib", "libfloat_mixer_plugin.dylib", "libstream_out_es_plugin.dylib", "libxml_plugin.dylib", "libaudioscrobbler_plugin.dylib", "libdemux_chromecast_plugin.dylib", "libplaylist_plugin.dylib", "libgradient_plugin.dylib", "libpacketizer_mpegaudio_plugin.dylib", "libsubtitle_plugin.dylib", "libmux_wav_plugin.dylib", "libmad_plugin.dylib", "libreal_plugin.dylib", "libtransform_plugin.dylib", "libopus_plugin.dylib", "libtcp_plugin.dylib", "libnsspeechsynthesizer_plugin.dylib", "libudp_plugin.dylib", "libnsv_plugin.dylib", "libpacketizer_vc1_plugin.dylib", "libdca_plugin.dylib", "libmpgv_plugin.dylib", "libstl_plugin.dylib", "libaddonsfsstorage_plugin.dylib", "libcdg_plugin.dylib", "libtrivial_channel_mixer_plugin.dylib", "libyuy2_i422_plugin.dylib", "librtpvideo_plugin.dylib", "libstats_plugin.dylib", "libmotion_plugin.dylib", "libpacketizer_dts_plugin.dylib", "libhds_plugin.dylib", "libmkv_plugin.dylib", "libcolorthres_plugin.dylib", "libheadphone_channel_mixer_plugin.dylib", "libmjpeg_plugin.dylib", "libtimecode_plugin.dylib", "libaccess_output_http_plugin.dylib", "libgain_plugin.dylib", "libmpc_plugin.dylib", "libaccess_output_shout_plugin.dylib", "libzvbi_plugin.dylib", "librotate_plugin.dylib", "libremoteosd_plugin.dylib", "libkeychain_plugin.dylib", "libcc_plugin.dylib", "libx265_plugin.dylib", "libftp_plugin.dylib", "libpostproc_plugin.dylib", "libconsole_logger_plugin.dylib", "libstream_out_rtp_plugin.dylib", "libbluescreen_plugin.dylib", "libgrain_plugin.dylib", "librawaud_plugin.dylib", "libmpg123_plugin.dylib", "libpacketizer_a52_plugin.dylib", "librss_plugin.dylib", "libaccess_output_srt_plugin.dylib", "libspudec_plugin.dylib", "libarchive_plugin.dylib", "libdvdread_plugin.dylib", "libty_plugin.dylib", "libi422_i420_plugin.dylib", "libvpx_plugin.dylib", "libprefetch_plugin.dylib", "libadjust_plugin.dylib", "libdummy_plugin.dylib", "libbluray-j2se-1.3.2.jar", "libtdummy_plugin.dylib", "libgoom_plugin.dylib", "libpodcast_plugin.dylib", "libmux_dummy_plugin.dylib", "libvhs_plugin.dylib", "libinflate_plugin.dylib", "libsid_plugin.dylib", "libgrey_yuv_plugin.dylib", "libmux_asf_plugin.dylib", "libdemuxdump_plugin.dylib", "liblua_plugin.dylib", "libtheora_plugin.dylib", "libsdp_plugin.dylib", "libvorbis_plugin.dylib", "libcvpx_plugin.dylib", "libspdif_plugin.dylib", "liboldrc_plugin.dylib", "libscte18_plugin.dylib", "libci_filters_plugin.dylib", "libsvcdsub_plugin.dylib", "libaraw_plugin.dylib", "libvcd_plugin.dylib", "libaccess_output_rist_plugin.dylib", "libchain_plugin.dylib", "libpacketizer_mpegvideo_plugin.dylib", "libscale_plugin.dylib", "libcompressor_plugin.dylib", "libosx_notifications_plugin.dylib", "libx264_plugin.dylib", "libaccess_realrtsp_plugin.dylib", "libcache_block_plugin.dylib", "libadpcm_plugin.dylib", "libyuvp_plugin.dylib", "libgradfun_plugin.dylib", "libcdda_plugin.dylib", "liboldmovie_plugin.dylib", "libdav1d_plugin.dylib", "libparam_eq_plugin.dylib", "libaiff_plugin.dylib", "libidummy_plugin.dylib", "libhttp_plugin.dylib", "librv32_plugin.dylib", "libaudiotoolboxmidi_plugin.dylib", "libes_plugin.dylib", "libjpeg_plugin.dylib", "libnetsync_plugin.dylib", "libfilesystem_plugin.dylib", "libstream_out_bridge_plugin.dylib", "libkate_plugin.dylib", "libaribsub_plugin.dylib", "libps_plugin.dylib", "liberase_plugin.dylib", "libadaptive_plugin.dylib", "librecord_plugin.dylib", "libstream_out_standard_plugin.dylib", "libsubstx3g_plugin.dylib", "libdeinterlace_plugin.dylib", "libuleaddvaudio_plugin.dylib", "libadf_plugin.dylib", "libschroedinger_plugin.dylib", "libmux_avi_plugin.dylib", "libdemux_cdg_plugin.dylib", "libimem_plugin.dylib", "liblibbluray_plugin.dylib", "libtelx_plugin.dylib", "libdemux_stl_plugin.dylib", "libts_plugin.dylib", "libextract_plugin.dylib", "libpacketizer_mpeg4audio_plugin.dylib", "libremap_plugin.dylib", "libaudiobargraph_v_plugin.dylib", "libpacketizer_hevc_plugin.dylib", "librist_plugin.dylib", "libfile_logger_plugin.dylib", "libtta_plugin.dylib", "libdirectory_demux_plugin.dylib", "libspatialaudio_plugin.dylib", "libsimple_channel_mixer_plugin.dylib", "libh26x_plugin.dylib", "libg711_plugin.dylib", "liblive555_plugin.dylib", "libttml_plugin.dylib", "libvdummy_plugin.dylib", "liblibmpeg2_plugin.dylib", "libogg_plugin.dylib", "libau_plugin.dylib", "libnsc_plugin.dylib", "libyuv_plugin.dylib", "libafile_plugin.dylib", "libstream_out_mosaic_bridge_plugin.dylib", "libmp4_plugin.dylib", "liboggspots_plugin.dylib", "libpacketizer_mpeg4video_plugin.dylib", "libavaudiocapture_plugin.dylib", "libncurses_plugin.dylib", "libdynamicoverlay_plugin.dylib", "libmacosx_plugin.dylib", "libshm_plugin.dylib", "libstream_out_chromecast_plugin.dylib", "libflacsys_plugin.dylib", "libaccess_mms_plugin.dylib", "libmod_plugin.dylib", "libscaletempo_plugin.dylib", "libsyslog_plugin.dylib", "libmagnify_plugin.dylib", "libstream_out_duplicate_plugin.dylib", "libsubsdelay_plugin.dylib", "libswscale_plugin.dylib", "libamem_plugin.dylib", "libstream_out_smem_plugin.dylib", "libspeex_resampler_plugin.dylib"
    };

    private final static String[] WIN_LIBS = {
            "libvlc.dll",
            "libvlccore.dll"
    };

    private void createTempFile() {
        if (RuntimeUtil.isMac()) {
            for (int i = 0; i < LIBS.length; i++) {
                LIBS[i] = "/vlc/macos/lib/" + LIBS[i];
            }
            for (int i = 0; i < PLUGINS.length; i++) {
                PLUGINS[i] = "/vlc/macos/plugins/" + PLUGINS[i];
            }

            tempFile.createTempFile(LIBS, "lib");
            tempFile.createTempFile(PLUGINS, "plugins");
        }
        if (RuntimeUtil.isWindows()) {
            for (int i = 0; i < LIBS.length; i++) {
                LIBS[i] = "/vlc/window/lib/" + LIBS[i];
            }
            tempFile.createTempFile(WIN_LIBS, "lib");
        }
    }

    public static void main(String[] args) {
        try (Stream<Path> paths = Files.walk(Paths.get("/Users/yuzhihao/aliyun/newjava/vlcj/src/main/resources/vlc/macos/plugins"))) {
            StringBuilder builder = new StringBuilder();
            paths.filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        builder.append("\"").append(filePath.getFileName()).append("\"").append(",");
                    });
            System.out.println(builder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
