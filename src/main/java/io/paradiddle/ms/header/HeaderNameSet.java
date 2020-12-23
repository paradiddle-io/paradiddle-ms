/*
 * Paradiddle MS - A lightweight microservices library with a comprehensible codebase.
 * Copyright (c) Michael Juliano 2020.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, write to:
 *
 * Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330
 * Boston, MA 02111-1307 USA
 */
package io.paradiddle.ms.header;

import io.paradiddle.ms.Header;
import io.paradiddle.ms.util.SetDelegate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class HeaderNameSet extends SetDelegate<HeaderName> implements HeaderNameCollection {
    public HeaderNameSet(final HeaderName... names) {
        super(Set.of(names));
    }

    @Override
    public boolean containsMatch(final Header header) {
        final Iterator<HeaderName> headers = this.iterator();
        boolean result = false;
        while (headers.hasNext() && !result) {
            final HeaderName name = headers.next();
            result = name.matches(header);
        }
        return result;
    }

    @Override
    public boolean containsMatch(final Map.Entry<String, String> header) {
        final Iterator<HeaderName> headers = this.iterator();
        boolean result = false;
        while (headers.hasNext() && !result) {
            final HeaderName name = headers.next();
            result = name.matches(header);
        }
        return result;
    }

    @Override
    public boolean doesNotContainMatch(final Header header) {
        return !this.containsMatch(header);
    }

    @Override
    public boolean doesNotContainMatch(final Map.Entry<String, String> header) {
        return !this.containsMatch(header);
    }
}
