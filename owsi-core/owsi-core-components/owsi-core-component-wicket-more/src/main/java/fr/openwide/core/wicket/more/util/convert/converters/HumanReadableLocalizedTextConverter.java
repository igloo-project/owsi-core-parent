/*
 * Copyright (C) 2009-2011 Open Wide
 * Contact: contact@openwide.fr
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.openwide.core.wicket.more.util.convert.converters;

import fr.openwide.core.wicket.more.rendering.LocalizedTextRenderer;

/**
 * @deprecated Use {@link LocalizedTextRenderer} instead.
 */
@Deprecated
public class HumanReadableLocalizedTextConverter extends LocalizedTextRenderer {
	
	private static final long serialVersionUID = -6397339082088737503L;
	
	private static HumanReadableLocalizedTextConverter INSTANCE = new HumanReadableLocalizedTextConverter();
	public static HumanReadableLocalizedTextConverter get() {
		return INSTANCE;
	}
	
	private HumanReadableLocalizedTextConverter() { }

}
