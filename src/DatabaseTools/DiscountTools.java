package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountTools {
    public static Discount retrieveRate(String discountCode){
        //create a discount
        Discount discount = null;
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT DiscountRate FROM Discount WHERE DiscountCode = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, discountCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                discount = new Discount(discountCode, resultSet.getDouble("DiscountRate"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return discount;
        }
    }
}
