package com.SAIAFarm.SAIAFarm;

import com.SAIAFarm.SAIAFarm.Response.SaiaFarmerData;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Utils.AppProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.wololo.geojson.Geometry;

import java.sql.*;
import java.util.ArrayList;
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

	//Create connection with MySQL
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

	//Get whole farmer data of farmers (for admin only)
	public ArrayList FarmerData() throws SQLException, ClassNotFoundException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {
			System.out.println("check point 1 all");

		//resultSet = statement.executeQuery("select head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates from sai.saia_farmers");
			//resultSet = statement.executeQuery("select * from sai.saia_farmers");
			resultSet = statement.executeQuery("select head_name, gender, son_wife_daughter, literacy, village, address_landmark, ST_AsText(farmer_address_coordinates)  from sai.saia_farmers");


			System.out.println("check point 2 all");

		//resultSet = statement.executeQuery("select * from sai.saia_farmers");

		ArrayList<SaiaFarmerData> msqlData = writeResultSet(resultSet);
			System.out.println("check point 3 all");


		//ArrayList<Comment> comment= new ArrayList<>();

		return msqlData;
	}}




	//write data in result set after getting farmer data from the sql query (for admin)
	private ArrayList<SaiaFarmerData> writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		ArrayList<SaiaFarmerData> farmerData = new ArrayList<>();

		while (resultSet.next()) {

			System.out.println(resultSet);

			String head_name = resultSet.getString("head_name");
			String gender = resultSet.getString("gender");
			String son_wife_daughter = resultSet.getString("son_wife_daughter");
			String literacy = resultSet.getString("literacy");
			String village = resultSet.getString("village");

			String address_landmark = resultSet.getString("address_landmark");
			System.out.println("check point 4 all");
			//Geometry farmer_address_coordinates = (Geometry) resultSet.getBlob("farmer_address_coordinates");
			//Blob farmer_address_coordinates = resultSet.getBlob("farmer_address_coordinates");
			//ArrayList farmer_address_coordinates = (ArrayList) resultSet.getArray(1);
			String farmer_address_coordinates = resultSet.getString("ST_AsText(farmer_address_coordinates)");
			System.out.println("Array: "+farmer_address_coordinates);
			System.out.println("check point 5 all");


			SaiaFarmerData cData = new SaiaFarmerData(resultSet.getString("head_name"), resultSet.getString("gender"), resultSet.getString("son_wife_daughter"),
					resultSet.getString("literacy"),resultSet.getString("village"),  resultSet.getString("address_landmark"), farmer_address_coordinates);

			//SaiaFarmerData cData = new SaiaFarmerData(resultSet.getString("head_name"), resultSet.getString("gender"),"","","","", farmer_address_coordinates);
			System.out.println("check point 6 all");
			farmerData.add(cData);

			list.add(resultSet.getString(2));

		}
		System.out.println(list);
		System.out.println(farmerData);
		return farmerData;

	}


	//Get farmer data of farmers (for single farmer)
	public ArrayList SingleFarmerDetail(int farmer_id) throws SQLException, ClassNotFoundException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {


			//resultSet = statement.executeQuery("select head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates from sai.saia_farmers");
			//resultSet = statement.executeQuery("select * from sai.saia_farmers");
			System.out.println("check1 for single farmer");
			String query = "select head_name, gender, son_wife_daughter, literacy, village, address_landmark, ST_AsText(farmer_address_coordinates)  from sai.saia_farmers WHERE farmer_id ="+farmer_id;
			//System.out.println("sql query " + pstmt.toString());
			resultSet = statement.executeQuery(query);
			System.out.println("check2 for single farmer");





			//resultSet = statement.executeQuery("select * from sai.saia_farmers");

			ArrayList<SaiaFarmerData> msqlData = writeResultSet(resultSet);



			//ArrayList<Comment> comment= new ArrayList<>();

			return msqlData;
		}}

	/*private ArrayList<SaiaFarmerData> writeResultSetForSingleFarmer(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		ArrayList<SaiaFarmerData> farmerData = new ArrayList<>();

		while (resultSet.next()) {

			System.out.println(resultSet);

			String head_name = resultSet.getString("head_name");
			String gender = resultSet.getString("gender");
			String son_wife_daughter = resultSet.getString("son_wife_daughter");
			String literacy = resultSet.getString("literacy");
			String village = resultSet.getString("village");

			String address_landmark = resultSet.getString("address_landmark");

			//Geometry farmer_address_coordinates = (Geometry) resultSet.getBlob("farmer_address_coordinates");
			//Blob farmer_address_coordinates = resultSet.getBlob("farmer_address_coordinates");
			//ArrayList farmer_address_coordinates = (ArrayList) resultSet.getArray(1);
			String farmer_address_coordinates = resultSet.getString("ST_AsText(farmer_address_coordinates)");



			SaiaFarmerData fData = new SaiaFarmerData(resultSet.getString("head_name"), resultSet.getString("gender"), resultSet.getString("son_wife_daughter"),
					resultSet.getString("literacy"),resultSet.getString("village"),  resultSet.getString("address_landmark"), farmer_address_coordinates);

			//SaiaFarmerData cData = new SaiaFarmerData(resultSet.getString("head_name"), resultSet.getString("gender"),"","","","", farmer_address_coordinates);

			farmerData.add(fData);

			list.add(resultSet.getString(2));






		}
		System.out.println(list);
		System.out.println(farmerData);
		return farmerData;

	}*/

	//insert farmer data (used in POST API's)
	public SaiaFarmerData farmerPostIntoDataBase(String head_name, String gender, String son_wife_daughter, String literacy, String village, String address_landmark, String farmer_address_coordinates) throws SQLException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		}
		//this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai?user=root&password=12345678");
		//System.out.println("check point 5");
		//this.statement = this.connect.createStatement();
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.comments values (default, ?, ?, ?, ? , ?, ?)");
		this.preparedStatement = this.connect.prepareStatement("insert into  sai.saia_farmers (head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates) VALUES (?, ?, ? , ?, ?, ?, ST_GeomFromGeoJSON(?))");
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.saia_farmers (head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates) VALUES (?, ?, ? , ?, ?, ?, ST_GeomFromText(?))");
		System.out.println("head_name," + head_name + " gender " + gender + " son_wife_daughter " + son_wife_daughter + " literacy " + literacy + " village " + village +   " address_landmark " + address_landmark + " farmer_address_coordinates " + farmer_address_coordinates );
		//String dateMysql = Utils.formatDate(date);
		this.preparedStatement.setString(1, head_name);
		this.preparedStatement.setString(2, gender);
		this.preparedStatement.setString(3, son_wife_daughter);
		this.preparedStatement.setString(4, literacy);
		this.preparedStatement.setString(5, village);
		this.preparedStatement.setString(6, address_landmark);
		this.preparedStatement.setString(7, farmer_address_coordinates);
		//this.preparedStatement.setObject(7, (Geometry) farmer_address_coordinates);

		this.preparedStatement.executeUpdate();
		SaiaFarmerData farmerPostData = new SaiaFarmerData(head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates);
		return farmerPostData;

		// You need to close the resultSet
	}

	//Get whole farm data of farmers (for admin only)
	public ArrayList FarmData() throws SQLException, ClassNotFoundException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}

		resultSet = statement.executeQuery("select * from sai.agri_farm");

		ArrayList<SaiaFarmData> msqlData = farmwriteResultSet(resultSet);

		//ArrayList<Comment> comment= new ArrayList<>();

		return msqlData;
	}

	//write data in result set after getting farm data from the sql query (for admin)
	private ArrayList<SaiaFarmData> farmwriteResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		ArrayList<SaiaFarmData> farmData = new ArrayList<>();

		while (resultSet.next()) {


			String farmsizecategory = resultSet.getString("farmsizecategory");
			String farm_name = resultSet.getString("farm_name");
			String farm_coordinates = resultSet.getString("farm_coordinates");
			String farm_type = resultSet.getString("farm_type");
			String farm_size = resultSet.getString("farm_size");



			SaiaFarmData cData = new SaiaFarmData(resultSet.getString("farmsizecategory"), resultSet.getString("farmsizecategory"), resultSet.getString("farm_name"), resultSet.getString("farm_coordinates"),
					resultSet.getString("farm_type"),resultSet.getString("farm_size"));
			farmData.add(cData);

			list.add(resultSet.getString(2));

		}
		System.out.println(list);
		System.out.println(farmData);
		return farmData;

	}

	//insert farm data (used in POST API's)
	public SaiaFarmData farmPostIntoDataBase(String farmer_id, String farmsizecategory, String farm_name, String farm_coordinates, String farm_type, String farm_size) throws SQLException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		}
		//this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai?user=root&password=12345678");
		//System.out.println("check point 5");
		//this.statement = this.connect.createStatement();
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.comments values (default, ?, ?, ?, ? , ?, ?)");
		this.preparedStatement = this.connect.prepareStatement("insert into  sai.agri_farm values (default, ?, ?, ?, ? , ?, ?)");
		System.out.println("farmer_id," + farmer_id +"farmsizecategory," + farmsizecategory + " farm_name " + farm_name + " farm_coordinates " + farm_coordinates + " farm_type " + farm_type + " farm_size " + farm_size);
		//String dateMysql = Utils.formatDate(date);



		this.preparedStatement.setString(1, farm_coordinates);
		this.preparedStatement.setString(2, farm_name);
		this.preparedStatement.setString(3, farm_size);
		this.preparedStatement.setString(4, farm_type);
		this.preparedStatement.setString(5, farmsizecategory);
		this.preparedStatement.setString(6, farmer_id);

		this.preparedStatement.executeUpdate();
		SaiaFarmData farmPostData = new SaiaFarmData(farmer_id, farmsizecategory, farm_name, farm_coordinates, farm_type, farm_size);
		return farmPostData;

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
