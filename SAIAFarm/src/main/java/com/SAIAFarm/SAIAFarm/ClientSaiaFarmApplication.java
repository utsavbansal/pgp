package com.SAIAFarm.SAIAFarm;

import com.SAIAFarm.SAIAFarm.Response.SaiaFarmerData;
import com.SAIAFarm.SAIAFarm.Utils.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class ClientSaiaFarmApplication {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private static ClientSaiaFarmApplication instance = null;
	public static ClientSaiaFarmApplication getInstance(){
		if (instance == null){
			synchronized (ClientSaiaFarmApplication.class){
				if (instance == null){
					instance =new ClientSaiaFarmApplication();
				}
			}
		}
		return instance;
	}
	public ClientSaiaFarmApplication(){

	}


	public static void main(String[] args) throws Exception {
		//SpringApplication.run(ClientSaiaFarmApplication.class, args);
		ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
		dao.FarmerData();
	}

	public boolean sqlconn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Properties props = new Properties();
			//String sqldriver = AppProperties.INSTANCE.getMysqlDriver();
			String sqlhost = AppProperties.INSTANCE.getMysqlUrl();
			String sqluser = AppProperties.INSTANCE.getMysqlUsername();
			String sqlpassword = AppProperties.INSTANCE.getMysqlPassword();

			connect = DriverManager.getConnection(sqlhost + "user="+sqluser + "&password="+sqlpassword);
			statement = connect.createStatement();
			System.out.println("Connection Established " +connect);
			return true;

		} catch (ClassNotFoundException | SQLException e){
			System.out.println("Exception " +e);
			return false;
		}

	}
	public ArrayList FarmerData() throws SQLException, ClassNotFoundException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}

		resultSet = statement.executeQuery("select * from sai.SaiaFarmer");

		ArrayList<SaiaFarmerData> msqlData = writeResultSet(resultSet);

		//ArrayList<Comment> comment= new ArrayList<>();

		return msqlData;
	}

	private ArrayList<SaiaFarmerData> writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		ArrayList<SaiaFarmerData> farmerData = new ArrayList<>();

		while (resultSet.next()) {

			String head_name = resultSet.getString("head_name");
			String gender = resultSet.getString("gender");
			String son_wife_daughter = resultSet.getString("son_wife_daughter");
			String literacy = resultSet.getString("literacy");
			String village = resultSet.getString("village");
			String farmer_address_coordinates = resultSet.getString("farmer_address_coordinates");
			String address_landmark = resultSet.getString("address_landmark");


			SaiaFarmerData cData = new SaiaFarmerData(resultSet.getString("head_name"), resultSet.getString("gender"), resultSet.getString("son_wife_daughter"),
					resultSet.getString("literacy"),resultSet.getString("village"), resultSet.getString("farmer_address_coordinates"), resultSet.getString("address_landmark"));
			farmerData.add(cData);

			list.add(resultSet.getString(2));

		}
		System.out.println(list);
		System.out.println(farmerData);
		return farmerData;

	}

	public SaiaFarmerData farmerPostIntoDataBase(String head_name, String gender, String son_wife_daughter, String literacy, String village, String farmer_address_coordinates, String address_landmark) throws SQLException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		}
		//this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai?user=root&password=12345678");
		//System.out.println("check point 5");
		//this.statement = this.connect.createStatement();
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.comments values (default, ?, ?, ?, ? , ?, ?)");
		this.preparedStatement = this.connect.prepareStatement("insert into  sai.SaiaFarmer values (default, ?, ?, ?, ? , ?, ?, ?)");
		System.out.println("head_name," + head_name + " gender " + gender + " son_wife_daughter " + son_wife_daughter + " literacy " + literacy + " village " + village + " farmer_address_coordinates " + farmer_address_coordinates + " address_landmark " + address_landmark);
		//String dateMysql = Utils.formatDate(date);
		this.preparedStatement.setString(1, head_name);
		this.preparedStatement.setString(2, gender);
		this.preparedStatement.setString(3, son_wife_daughter);
		this.preparedStatement.setString(4, literacy);
		this.preparedStatement.setString(5, village);
		this.preparedStatement.setString(6, farmer_address_coordinates);
		this.preparedStatement.setString(7, address_landmark);
		this.preparedStatement.executeUpdate();
		SaiaFarmerData farmerPostData = new SaiaFarmerData(head_name, gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
		return farmerPostData;

		// You need to close the resultSet
	}
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
