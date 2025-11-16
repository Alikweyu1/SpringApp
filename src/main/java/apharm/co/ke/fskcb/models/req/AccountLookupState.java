package apharm.co.ke.fskcb.models.req;

public enum AccountLookupState
{

    NOT_FOUND(0),
    ACTIVE(1),
    NOT_ACTIVATED(2);

    private int state;

    AccountLookupState(int s) {
        this.state = s;
    }

    public int getState() {
        return state;
    }
}
