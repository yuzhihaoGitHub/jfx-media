/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009-2020 Caprica Software Limited.
 */

package com.yuzhihao.learn.ui.view.play.controls.volume;

import javafx.scene.image.Image;

final class VolumeMaxButton extends VolumeButton {

    private static final Image IMAGE = getImage("/images/icons/buttons/volume_max_18dp.png");

    VolumeMaxButton(Runnable action) {
        super(action, IMAGE);
    }
}
