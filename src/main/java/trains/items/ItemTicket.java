package trains.items;

import java.util.UUID;

public class ItemTicket extends ItemKey {

    /**
     * <h2>constructor</h2>
     * @param playerOwner defines the creator instead of the rollingstock to prevent forgery.
     * @param ticketName defines the name displayed on the ticket, this is not only used for display but also to check if tickets match.
     */
    public ItemTicket(UUID playerOwner, String ticketName){
        super(playerOwner, ticketName);
    }

}
