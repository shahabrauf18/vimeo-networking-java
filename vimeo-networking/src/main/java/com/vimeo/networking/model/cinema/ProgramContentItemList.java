/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Vimeo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vimeo.networking.model.cinema;

import com.vimeo.networking.model.BaseResponseList;
import com.vimeo.stag.UseStag;
import com.vimeo.stag.UseStag.FieldOption;

import java.io.Serializable;

/**
 * A {@link BaseResponseList} of {@link ProgramContentItem}s
 * Created by rigbergh on 3/1/17.
 */
@UseStag(FieldOption.SERIALIZED_NAME)
public class ProgramContentItemList extends BaseResponseList<ProgramContentItem> implements Serializable {

    private static final long serialVersionUID = 3750546702306457270L;

    @Override
    public Class<ProgramContentItem> getModelClass() {
        return ProgramContentItem.class;
    }
}
