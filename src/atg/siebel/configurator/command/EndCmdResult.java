package atg.siebel.configurator.command;

import atg.commerce.order.CommerceItem;
import atg.commerce.order.Order;

import java.util.Collection;

/**
 * User: Anton Gavazuk
 * Mail: anton.gavazuk@oracle.com
 * Date: 10/03/11
 * Time: 14:05
 * Created for Oracle ATG Swisscom PoC
 */
public class EndCmdResult extends CommandResult {

    /**
     * Affected atg items
     */
    private Collection<CommerceItem> affectedItems;
    private Order order;

    public EndCmdResult() {

    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
