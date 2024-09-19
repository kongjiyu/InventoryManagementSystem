package DatabaseTools;

import Entity.Warehouse;

public interface WarehouseService {
    // get warehouse entity by listing down to let user choose
    public Warehouse getWarehouseByList();

    // get the warehouse entity by refering ID
    public Warehouse getWarehouseById(String ID);

    // get the warehouse entity by refering name
    public Warehouse getWarehouseByName(String name);
}
