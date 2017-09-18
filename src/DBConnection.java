
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;



public class DBConnection {
	
    private String protocol = "jdbc:derby:";
    private String DBlocation = "DentistDB";
    private Connection connection = null;
    public DBConnection(String name, String password){
        try{
            connection = DriverManager.getConnection( protocol + DBlocation , name, password);
        }catch(SQLException  e){
            System.out.println("Database does not exist\nCreating datatbase.......");
            try {
                connection = DriverManager.getConnection( protocol + DBlocation + ";create=true" , name, password );
                createDatabase();
            }catch (SQLException ex) {
                System.out.println("Database " + DBlocation + " failed to create");
                ex.printStackTrace();
            }
            System.out.println("Database " + DBlocation + " created");
        }
    }
    
    public Connection getConnection(){
        return this.connection;
    }
    
    public void closeConnection(){
        try {
            this.getConnection().close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void closeStatment(Statement statement){
        try {
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Boolean isTableEmpty(String table){
        
        Boolean isEmpty = true;
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        
        sql = "SELECT * FROM " + table;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            
            if(resultSet.next() == false){
                isEmpty = true;
            }else{isEmpty = false;}
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        
        return isEmpty;
    }
    
    public ArrayList<String> getStringDataColumn(String column, String table){//parameters Strings type of name, name of table
        ArrayList<String> data = new ArrayList<>();
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + column + " FROM " + table;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        
        
        return data;
    }
    
    public ArrayList<Double> getDoubleDataColumn(String column, String table){//parameters Strings type of name, name of table
        ArrayList<Double> data = new ArrayList<>();
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + column + " FROM " + table;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data.add(resultSet.getDouble(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        
        return data;
    }
    
    public ArrayList<String> getStringDataColumnWithWhere(String column, String table, String where, String value){//parameters Strings type of name, name of table
        ArrayList<String> data = new ArrayList<>();
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + column + " FROM " + table + " WHERE " + where + " = '" + value + "'";
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    public ArrayList<String> getStringDataColumnWithWhereSortNum(String column, String table, String where, String value){//parameters Strings type of name, name of table
        ArrayList<String> data = new ArrayList<>();
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + column + " FROM " + table + " WHERE " + where + " = '" + value + "' ORDER BY patientNumber ASC";
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public ArrayList<Integer> getIntDataColumnWithWhere(String column, String table, String where, int value){//parameters Strings type of name, name of table
        ArrayList<Integer> data = new ArrayList<>();
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + column + " FROM " + table + " WHERE " + where + " = " + value;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data.add(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public int getListSizeWithString(String table, String where, String value){//parameters Strings type of name, name of table
        int data = 0;
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT * FROM " + table + " WHERE " + where + " = '" + value + "'";
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public int getListSizeWithInt(String table, String where, int value){//parameters Strings type of name, name of table
        int data = 0;
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT * FROM " + table + " WHERE " + where + " = " + value;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public String getStringData(String select, String from, String where, String value){//parameters Strings type of name, name of table
        String data = "";
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + select + " FROM " + from + " WHERE " + where + " = '" + value +  "'";
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data = resultSet.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public String getStringDataWithInt(String select, String from, String where, int value){//parameters Strings type of name, name of table
        String data = "";
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + select + " FROM " + from + " WHERE " + where + " = " + value;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data = resultSet.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public Timestamp getDateWithInt(String select, String from, String where, int value){//parameters Strings type of name, name of table
        Timestamp data = null;
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + select + " FROM " + from + " WHERE " + where + " = " + value;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data = resultSet.getTimestamp(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public double getDoubleWithInt(String select, String from, String where, int value){//parameters Strings type of name, name of table
        double data = 0.0;
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + select + " FROM " + from + " WHERE " + where + " = " + value;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data = resultSet.getDouble(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public Boolean getBooleanWithInt(String select, String from, String where, int value){//parameters Strings type of name, name of table
        Boolean data = null;
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + select + " FROM " + from + " WHERE " + where + " = " + value;
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data = resultSet.getBoolean(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    
    
    public int getIntData(String select, String from, String where, String value){//parameters Strings type of name, name of table
        int data = -1;
        String sql;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT " + select + " FROM " + from + " WHERE " + where + " = '" + value +  "'";
        try {
            statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                data = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        return data;
    }
    
    public void removeRowWithString(String from, String where, String value){//parameters Strings type of name, name of table
        String sql, sqlDel;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT * FROM " + from + " WHERE " + where + " = '" + value +  "'";
        sqlDel = "DELETE FROM " + from + " WHERE " + where + " = '" + value + "'";
        try {
            statement = this.getConnection().createStatement();
            statement.execute(sqlDel);
            //while(resultSet.next()){
            //    resultSet.deleteRow();
            //}
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        
    }
    
    public void removeRowWithInt(String from, String where, int value){//parameters Strings type of name, name of table
        String sql, sqlDel;
        Statement statement = null;
        ResultSet resultSet;
        sql = "SELECT * FROM " + from + " WHERE " + where + " = " + value;
        sqlDel = "DELETE FROM " + from + " WHERE " + where + " = " + value;
        try {
            statement = this.getConnection().createStatement();
            statement.execute(sqlDel);
            //while(resultSet.next()){
            //    resultSet.deleteRow();
            //}
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(statement);
        }
        
    }
    
    
    
    public void addToPatientTable(String name, String address, String dentist){
        Statement update = null;
        try {
            update = this.getConnection().createStatement();
            String sql;
            sql= "INSERT INTO Patients(patientName, patientAddress, patientDentist)"
                    + " VALUES('" + name + "','" + address + "','" + dentist + "')";
            update.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeStatment(update);
        }
    }
    
    public void addToInvoiceTable(Date date, int patNum){
        Statement update = null;
        try {
            update = this.getConnection().createStatement();
            String sql;
            sql= "INSERT INTO Invoices(invoiceAmount ,invoiceDate, invoicePaid, invoicePatient)"
                    + " VALUES(0.0, '" + date.toString() + "', false, " + patNum + ")";
            update.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeStatment(update);
        }
    }
    
    public void updatePaid(Boolean value, int num){
        Statement update = null;
        try{
            update = this.getConnection().createStatement();
            String sql;
            sql= "UPDATE Invoices SET invoicePaid = " + value + " WHERE invoiceNumber = " + num;
            update.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            this.closeStatment(update);
        }
    }
    
    public void addToProcedureTable(String name, double cost, int invNum){
        Statement update = null;
        try {
            update = this.getConnection().createStatement();
            String sql;
            sql= "INSERT INTO Procedures(procedureName, procedureCost, procedureInvoice)"
                    + " VALUES('" + name + "'," + cost + "," + invNum + ")";
            update.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeStatment(update);
        }
    }
    
    public void addToPaymentTable(Double amount, Date date, int invNum){
        Statement update = null;
        try {
            update = this.getConnection().createStatement();
            String sql;
            sql= "INSERT INTO Payments(paymentAmount, paymentDate, paymentInvoice)"
                    + " VALUES(" + amount + ",'" + date.toString() + "'," + invNum + ")";
            update.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeStatment(update);
        }
    }
    
    public void addToProcedureTypeTable(String name, double cost){ 
        Statement update = null;                                   
        try {
            update = this.getConnection().createStatement();
            String sql;
            sql= "INSERT INTO ProcedureTypes(typeName, typeCost)"
                    + " VALUES('" + name + "'," + cost + ")";
            update.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeStatment(update);
        }
    }
       
    private void createDatabase(){
        Statement initialiseTable = null;
        try {
            initialiseTable = this.getConnection().createStatement();
            String tableUpdate;
            tableUpdate = "CREATE TABLE Dentists(\n" +
                            "    userName VARCHAR(20) NOT NULL,\n" +
                            "    userAddress VARCHAR(20),\n" +
                            "    userPassword varchar(20),\n" +
                            "    PRIMARY KEY(userName)\n" +
                            ")";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "CREATE TABLE Patients(\n" +
                            "    patientNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),\n" +
                            "    patientName VARCHAR(20),\n" +
                            "    patientAddress VARCHAR(20),\n" +
                            "    patientDentist VARCHAR(20),\n" +
                            "    PRIMARY KEY(patientNumber)\n" +
                            ")";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "CREATE TABLE Invoices(\n" +
                            "    invoiceNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),\n" +
                            "    invoiceAmount DOUBLE,\n" +
                            "    invoiceDate VARCHAR(50),\n" +
                            "    invoicePaid BOOLEAN,\n" +
                            "    invoicePatient INT,\n" +
                            "    PRIMARY KEY(invoiceNumber)\n" +
                            ")";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "CREATE TABLE Procedures(\n" +
                            "    procedureNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),\n" +
                            "    procedureName VARCHAR(20),\n" +
                            "    procedureCost DOUBLE,\n" +
                            "    procedureInvoice INT,\n" +
                            "    PRIMARY KEY(procedureNumber)\n" +
                            ")";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "CREATE TABLE Payments(\n" +
                            "    paymentNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),\n" +
                            "    paymentAmount DOUBLE,\n" +
                            "    paymentDate VARCHAR(50),\n" +
                            "    paymentInvoice INT,\n" +
                            "    PRIMARY KEY(paymentNumber)\n" +
                            ")";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "CREATE TABLE ProcedureTypes(\n" +
                            "    procedureTypeNumber INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),\n" +
                            "    typeName VARCHAR(20),\n" +
                            "    typeCost DOUBLE,\n" +
                            "    PRIMARY KEY(procedureTypeNumber)\n" +
                            ")";
            initialiseTable.executeUpdate(tableUpdate);

            tableUpdate = "INSERT INTO Dentists VALUES('dentist00','address00','qwe')";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO Dentists VALUES('dentist01','address01','qwe')";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO Dentists VALUES('dentist02','address02','qwe')";
            initialiseTable.executeUpdate(tableUpdate);

            tableUpdate = "INSERT INTO ProcedureTypes(typeName, typeCost) VALUES('procType00',50.0)";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO ProcedureTypes(typeName, typeCost) VALUES('procType01',100.0)";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO ProcedureTypes(typeName, typeCost) VALUES('procType02',150.0)";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO ProcedureTypes(typeName, typeCost) VALUES('procType03',200.0)";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO ProcedureTypes(typeName, typeCost) VALUES('procType04',250.0)";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO ProcedureTypes(typeName, typeCost) VALUES('procType05',300.0)";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO ProcedureTypes(typeName, typeCost) VALUES('procType06',400.0)";
            initialiseTable.executeUpdate(tableUpdate);
            tableUpdate = "INSERT INTO ProcedureTypes(typeName, typeCost) VALUES('procType07',600.0)";
            initialiseTable.executeUpdate(tableUpdate);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            this.closeStatment(initialiseTable);
        }
                
    }

}
