/*
 * Copyright 2014 Sebastian Annies
 *
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.sannies.isoviewer;

import com.googlecode.mp4parser.authoring.tracks.h265.H265NalUnitTypes;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * Show some details of the H265 NAL
 */
public class HvcNalWrapper {
    ByteBuffer data;
    private int nal_ref_idc;
    private int nal_unit_type;

    public HvcNalWrapper(ByteBuffer data) {
        this.data = data;
        int type = data.get();
        this.nal_ref_idc = (type >> 5) & 3;
        this.nal_unit_type = type & 0x1f;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Field f : H265NalUnitTypes.class.getFields()) {
            try {
                if (nal_unit_type ==  f.getInt(null)) {
                    sb.append(f.getName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException();
            }
        }
        sb.append("{").append("type:").append(nal_unit_type).append(",size:").append(data.limit());
        sb.append('}');
        return sb.toString();
    }
}
