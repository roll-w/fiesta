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
