/*
 * Copyright (C) 2022 Lingu
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

package space.lingu.fiesta.compile;

import space.lingu.InfoPolicy;

/**
 * The type of element in the call chain.
 *
 * @author RollW
 */
public enum ChainType {
    SELF,
    CALLER;

    public boolean shouldOutput(InfoPolicy policy) {
        if (policy == InfoPolicy.NONE) {
            return false;
        }
        if(policy == InfoPolicy.ALL) {
            return true;
        }
        switch (this) {
            case SELF:
                return policy != InfoPolicy.CALLER;
            case CALLER:
                return policy != InfoPolicy.SELF;
            default:
                throw new IllegalStateException();
        }
    }
}
