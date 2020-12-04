import org.junit.Test;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Store;
import ru.durnov.warehouse.print.ViewForm;

public class TestViewForm {

    @Test
    public void testViewForm(){
        Order order = new Order(1, new Store("Ферма №1"));
        ViewForm viewForm = new ViewForm(order);
        viewForm.show();
    }
}
