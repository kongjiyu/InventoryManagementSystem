package DatabaseTools;

import Entity.Retailer;

public interface RetailerService {
    // get the retailer entity by using ID
    public Retailer getRetailerById(String ID);

    // get retailer by refering the name
    public Retailer getRetailerByName(String name);
}
