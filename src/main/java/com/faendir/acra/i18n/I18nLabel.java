/*
 * (C) Copyright 2018 Lukas Morawietz (https://github.com/F43nd1r)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.faendir.acra.i18n;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.i18n.support.Translatable;

import java.util.Locale;

/**
 * @author lukas
 * @since 15.08.18
 */
public class I18nLabel extends Label implements Translatable {
    private final I18N i18n;
    private final String captionId;
    private final Object[] params;

    public I18nLabel(ContentMode contentMode, I18N i18n, String captionId, Object... params) {
        this.i18n = i18n;
        this.captionId = captionId;
        this.params = params;
        setContentMode(contentMode);
        updateMessageStrings(i18n.getLocale());
    }

    public I18nLabel(I18N i18n, String captionId, Object... params) {
        this(ContentMode.TEXT, i18n, captionId, params);
    }

    @Override
    public void updateMessageStrings(Locale locale) {
        setValue(i18n.get(captionId, locale, params));
    }
}
