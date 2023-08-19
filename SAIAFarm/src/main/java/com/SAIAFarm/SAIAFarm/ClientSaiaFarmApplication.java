package com.SAIAFarm.SAIAFarm;

import com.SAIAFarm.SAIAFarm.Response.SaiaFarmerData;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaiParcelData;
import com.SAIAFarm.SAIAFarm.Utils.AppProperties;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.json.JSONArray;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.wololo.geojson.Geometry;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

	//Create connection with MySQL
	public boolean sqlconn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Class.forName("com.mysql.jdbc.Driver");
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
	public ArrayList FarmerData() throws SQLException, ClassNotFoundException, ParseException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {
			System.out.println("check point 1 all");

		//resultSet = statement.executeQuery("select head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates from sai.saia_farmers");
			//resultSet = statement.executeQuery("select * from sai.saia_farmers");
			resultSet = statement.executeQuery("select user_id, farmer_id, type, head_name, gender, son_wife_daughter, literacy, ST_AsText(farmer_address_coordinates,'axis-order=long-lat'), address, date_created, date_modified  from sai.saia_farmers");


			System.out.println("check point 2 all");

		//resultSet = statement.executeQuery("select * from sai.saia_farmers");

		ArrayList<SaiaFarmerData> msqlData = writeResultSet(resultSet);
			System.out.println("check point 3 all");


		//ArrayList<Comment> comment= new ArrayList<>();

		return msqlData;
	}}




	//write data in result set after getting farmer data from the sql query (for admin)
	/*private ArrayList<SaiaFarmerData> writeResultSet(ResultSet resultSet) throws SQLException {
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
			String farmer_address_coordinates = resultSet.getString("ST_AsText(farmer_address_coordinates,'axis-order=long-lat')");
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

	}*/
  /////////writeset result for json
	private ArrayList<SaiaFarmerData> writeResultSet(ResultSet resultSet) throws SQLException, ParseException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		ArrayList<SaiaFarmerData> farmerData = new ArrayList<>();

		while (resultSet.next()) {

			System.out.println(resultSet);

			String user_id = resultSet.getString("user_id");
			String farmer_id = resultSet.getString("farmer_id");
			String type = resultSet.getString("type");
			String head_name = resultSet.getString("head_name");
			String gender = resultSet.getString("gender");
			String son_wife_daughter = resultSet.getString("son_wife_daughter");
			String literacy = resultSet.getString("literacy");
			//String village = resultSet.getString("village");

			//String address_landmark = resultSet.getString("address_landmark");
			String address = resultSet.getString("address");
			System.out.println("check point 4 all");
			String date_created = resultSet.getString("date_created");
			String date_modified = resultSet.getString("date_modified");
			//Geometry farmer_address_coordinates = (Geometry) resultSet.getBlob("farmer_address_coordinates");
			//Blob farmer_address_coordinates = resultSet.getBlob("farmer_address_coordinates");
			//ArrayList farmer_address_coordinates = (ArrayList) resultSet.getArray(1);
			String farmer_address_coordinates = resultSet.getString("ST_AsText(farmer_address_coordinates,'axis-order=long-lat')");
			System.out.println("Array: "+farmer_address_coordinates);
			System.out.println("check point 5 all");


			String wktString = farmer_address_coordinates;
			WKTReader reader = new WKTReader();
			Geometry geom = reader.read(wktString);

			System.out.println("return to chk json");
			System.out.println(geom.getGeometryType());
			//System.out.println(Arrays.stream(geom.getCoordinates()).toString());
			System.out.println(geom.getCoordinate());
			System.out.println(geom.getCoordinate().toString());
			//System.out.println(Arrays.stream(geom.getCoordinates()).toArray().toString());
			System.out.println(geom.getCoordinates().toString());





			SaiaFarmerData cData = new SaiaFarmerData(resultSet.getString("user_id"),resultSet.getString("farmer_id"),resultSet.getString("type"),resultSet.getString("head_name"), resultSet.getString("gender"), resultSet.getString("son_wife_daughter"),
					resultSet.getString("literacy"), farmer_address_coordinates, resultSet.getString("address"),resultSet.getString("date_created"), resultSet.getString("date_modified"));

//			SaiaFarmerData cData = new SaiaFarmerData(resultSet.getString("farmer_id"),resultSet.getString("type"),resultSet.getString("head_name"), resultSet.getString("gender"), resultSet.getString("son_wife_daughter"),
//					resultSet.getString("literacy"), farmer_address_coordinates, resultSet.getString("address"));

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
	public ArrayList SingleFarmerDetail(int farmer_id) throws SQLException, ClassNotFoundException, ParseException {


		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {


			//resultSet = statement.executeQuery("select head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates from sai.saia_farmers");
			//resultSet = statement.executeQuery("select * from sai.saia_farmers");
			System.out.println("check1 for single farmer");
			//String query = "select head_name, gender, son_wife_daughter, literacy, village, address_landmark, ST_AsText(farmer_address_coordinates,'axis-order=long-lat')  from sai.saia_farmers WHERE farmer_id ="+farmer_id;
			String query = "select user_id, farmer_id, type, head_name, gender, son_wife_daughter, literacy, address, ST_AsText(farmer_address_coordinates,'axis-order=long-lat'), date_created, date_modified from sai.saia_farmers WHERE farmer_id ="+farmer_id;
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
			String farmer_address_coordinates = resultSet.getString("ST_AsText(farmer_address_coordinates,'axis-order=long-lat')");



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
/*	public SaiaFarmerData farmerPostIntoDataBase(String head_name, String gender, String son_wife_daughter, String literacy, String village, String address_landmark, String farmer_address_coordinates) throws SQLException {
		boolean connectionValue = this.sqlconn();
		System.out.println("cheking properties sql");
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		}
		//this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai?user=root&password=12345678");
		//System.out.println("check point 5");
		//this.statement = this.connect.createStatement();
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.comments values (default, ?, ?, ?, ? , ?, ?)");
		this.preparedStatement = this.connect.prepareStatement("insert into  sai.saia_farmers (head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates, updated_time) VALUES (?, ?, ? , ?, ?, ?, ST_GeomFromGeoJSON(?), now())");
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.agri_farm (farmer_id, farmsizecategory, farm_name, farm_type, farm_size, farm_coordinates, created_time, updated_time, user_id) VALUES (?, ?, ? , ?, ?, ST_GeomFromGeoJSON(?), now(), now(), ?)");
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
	}*/

	////for Json
	public List<SaiaFarmerData> farmerPostIntoDataBase(String user_id, String head_name, String gender, String son_wife_daughter, String literacy, String farmer_address_coordinates, String address) throws SQLException, ParseException {
		boolean connectionValue = this.sqlconn();
		System.out.println("cheking properties sql");
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		}
		//this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai?user=root&password=12345678");
		//System.out.println("check point 5");
		//this.statement = this.connect.createStatement();
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.comments values (default, ?, ?, ?, ? , ?, ?)");
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.saia_farmers (head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates, updated_time) VALUES (?, ?, ? , ?, ?, ?, ST_GeomFromGeoJSON(?), now())");
		///this.preparedStatement = this.connect.prepareStatement("insert into  sai.saia_farmers (head_name, gender, son_wife_daughter, literacy, farmer_address_coordinates, address, date_modified) VALUES (?, ?, ? , ?, ST_GeomFromGeoJSON(?), ?, now())");
		this.preparedStatement = this.connect.prepareStatement("insert into  sai.saia_farmers (user_id, head_name, gender, son_wife_daughter, literacy, farmer_address_coordinates, address) VALUES (?, ?, ?, ? , ?, ST_GeomFromGeoJSON(?), ?)");
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.agri_farm (farmer_id, farmsizecategory, farm_name, farm_type, farm_size, farm_coordinates, created_time, updated_time, user_id) VALUES (?, ?, ? , ?, ?, ST_GeomFromGeoJSON(?), now(), now(), ?)");
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.saia_farmers (head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates) VALUES (?, ?, ? , ?, ?, ?, ST_GeomFromText(?))");
		//System.out.println("head_name," + head_name + " gender " + gender + " son_wife_daughter " + son_wife_daughter + " literacy " + literacy + " village " + village +   " address_landmark " + address_landmark + " farmer_address_coordinates " + farmer_address_coordinates );
		System.out.println("user_id," + user_id + "head_name," + head_name + " gender " + gender + " son_wife_daughter " + son_wife_daughter + " literacy " + literacy +  " farmer_address_coordinates " + farmer_address_coordinates );
		//String dateMysql = Utils.formatDate(date);
		this.preparedStatement.setString(1, user_id);
		this.preparedStatement.setString(2, head_name);
		this.preparedStatement.setString(3, gender);
		this.preparedStatement.setString(4, son_wife_daughter);
		this.preparedStatement.setString(5, literacy);
		//this.preparedStatement.setString(5, village);
		//this.preparedStatement.setString(6, address_landmark);
		this.preparedStatement.setString(6, farmer_address_coordinates);
		this.preparedStatement.setString(7, address);
		//this.preparedStatement.setObject(7, (Geometry) farmer_address_coordinates);

		this.preparedStatement.executeUpdate();
	//	SaiaFarmerData farmerPostData = new SaiaFarmerData(user_id, head_name, gender, son_wife_daughter, literacy, farmer_address_coordinates, address);
	//	return farmerPostData;
		String query2 = "select user_id, farmer_id, type, head_name, gender, son_wife_daughter, literacy, address, ST_AsText(farmer_address_coordinates,'axis-order=long-lat'), date_created, date_modified from sai.saia_farmers ORDER BY farmer_id DESC LIMIT 1";
		this.resultSet = this.statement.executeQuery(query2);
		List<SaiaFarmerData> farmerPostData=this.writeResultSet(this.resultSet);
		return farmerPostData;
		// You need to close the resultSet
	}

	//Get whole farm data of farmers (for admin only)
	public List<SaiaFarmData> FarmData() throws SQLException, ClassNotFoundException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}

		resultSet = statement.executeQuery("select user_id, farm_id, farmer_id, type, farm_name, farmsizecategory, farm_type, farm_size, address, ST_AsText(farm_coordinates,'axis-order=long-lat'), date_created, date_modified  from sai.agri_farm");
		List<SaiaFarmData> retSariFarm = new ArrayList<>();
		retSariFarm = farmwriteResultSet(resultSet);

		//ArrayList<Comment> comment= new ArrayList<>();

		return retSariFarm;
	}

	//write data in result set after getting farm data from the sql query (for admin)
	private List<SaiaFarmData> farmwriteResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		List<SaiaFarmData> farmData = new ArrayList<>();
		boolean isFarmsExist = false;
		while (resultSet.next()) {

			String farmer_id =resultSet.getString("farmer_id");
			String user_id =resultSet.getString("user_id");
			String farm_id =resultSet.getString("farm_id");
			String type = resultSet.getString("type");
			String farmsizecategory = resultSet.getString("farmsizecategory");
			String farm_name = resultSet.getString("farm_name");
			String farm_coordinates = resultSet.getString("ST_AsText(farm_coordinates,'axis-order=long-lat')");
			//String farm_coordinates = resultSet.getString("farm_coordinates");
			String farm_type = resultSet.getString("farm_type");
			String farm_size = resultSet.getString("farm_size");
			String address =resultSet.getString("address");
			String date_created = resultSet.getString("date_created");
			String date_modified = resultSet.getString("date_modified");
			list.add(farm_name);

			/*SaiaFarmerData cData = new SaiaFarmerData(resultSet.getString("user_id"),resultSet.getString("farmer_id"),resultSet.getString("type"),resultSet.getString("head_name"), resultSet.getString("gender"), resultSet.getString("son_wife_daughter"),
					resultSet.getString("literacy"), farmer_address_coordinates, resultSet.getString("address"),resultSet.getString("date_created"), resultSet.getString("date_modified"));*/
			/*SaiaFarmData cData = new SaiaFarmData(resultSet.getString("user_id"),resultSet.getString("farmer_id"),resultSet.getString("type"),resultSet.getString("farmsizecategory"), resultSet.getString("farm_name"), "farm_coordinates",
					resultSet.getString("farm_type"),resultSet.getString("farm_size"), resultSet.getString("address"),resultSet.getString("date_created"), resultSet.getString("date_modified"));*/
			SaiaFarmData cData = new SaiaFarmData(resultSet.getString("farm_id"),resultSet.getString("farmer_id"),resultSet.getString("type"),resultSet.getString("farmsizecategory"), resultSet.getString("farm_name"), resultSet.getString("farm_type"), resultSet.getString("farm_size"), farm_coordinates,
					resultSet.getString("user_id"), resultSet.getString("address"),resultSet.getString("date_created"), resultSet.getString("date_modified"));
//			SaiaFarmData cData = new SaiaFarmData(resultSet.getString("farmsizecategory"), resultSet.getString("farm_name"),"farm_coordinates",
//					resultSet.getString("farm_type"),resultSet.getString("farm_size"));
//			SaiaFarmData cData = new SaiaFarmData(farmsizecategory, farm_name,farm_coordinates, farm_type, farm_size);

			farmData.add(cData);

			//list.add(resultSet.getString(2));

		}
		int size = list.size();
		if(size >=1)
		{
			isFarmsExist = true;
			System.out.println("eixit farm name: "+list);

		}
		System.out.println("is farm already exist: "+isFarmsExist);
		return farmData;

	}

	//insert farm data (used in POST API's)
	public List<SaiaFarmData> farmPostIntoDataBase(String farmer_id, String farmsizecategory, String farm_name, String farm_type, String farm_size, String farm_coordinates, String userId, String address) throws SQLException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		}
		//this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai?user=root&password=12345678");
		//System.out.println("check point 5");
		//this.statement = this.connect.createStatement();
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.comments values (default, ?, ?, ?, ? , ?, ?)");
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.agri_farm values (default, ?, ?, ?, ? , ?, ?)");
		//this.preparedStatement = this.connect.prepareStatement("insert into  sai.agri_farm (farmer_id, farmsizecategory, farm_name, farm_type, farm_size, farm_coordinates) VALUES (?, ?, ? , ?, ?, ST_GeomFromGeoJSON(?))");
		this.preparedStatement = this.connect.prepareStatement("insert into  sai.agri_farm (farmer_id, farmsizecategory, farm_name, farm_type, farm_size, farm_coordinates, user_id, address) VALUES (?, ?, ? , ?, ?, ST_GeomFromGeoJSON(?), ?, ?)");
		System.out.println("farmer_id," + farmer_id +"farmsizecategory," + farmsizecategory + " farm_name " + farm_name + " farm_type " + farm_type + " farm_size " + farm_size + " farm_coordinates " + farm_coordinates + " address " + address);
		//String dateMysql = Utils.formatDate(date);


		this.preparedStatement.setString(1, farmer_id);
		this.preparedStatement.setString(2, farmsizecategory);
		this.preparedStatement.setString(3, farm_name);
		this.preparedStatement.setString(4, farm_type);
		this.preparedStatement.setString(5, farm_size);
		this.preparedStatement.setString(6, farm_coordinates);
		this.preparedStatement.setString(7, userId);
		this.preparedStatement.setString(8, address);

		this.preparedStatement.executeUpdate();

		String query2 = "select user_id, farm_id, farmer_id, type, farm_name, farmsizecategory, farm_type, farm_size, address, ST_AsText(farm_coordinates,'axis-order=long-lat'), date_created, date_modified  from sai.agri_farm ORDER BY farm_id DESC LIMIT 1";
		this.resultSet = this.statement.executeQuery(query2);
		List<SaiaFarmData> farmPostData=this.farmwriteResultSet(this.resultSet);
		return farmPostData;
		// You need to close the resultSet
	}

	//To check farm data exist or not(provided at the time of insertion of adding farm by the user)
	public List<SaiaFarmData> CheckFarmDataDuplicate(String userId, String farmName) throws SQLException, ClassNotFoundException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {


			//resultSet = statement.executeQuery("select head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates from sai.saia_farmers");
			//resultSet = statement.executeQuery("select * from sai.saia_farmers");
			//String query = "select user_id, farm_name, farm_size, farm_type, farmsizecategory, farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE farmer_id =+userId";
			String query = "select user_id, farm_name, farm_size, farm_type, farmsizecategory, farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE user_id ='" + userId + "' AND farm_name='" + farmName + "'";

			//resultSet = statement.executeQuery("select user_id, farm_name, farm_size, farm_type, farmsizecategory, farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE farmer_id ="+farmer_id);
			resultSet = statement.executeQuery(query);




			//resultSet = statement.executeQuery("select * from sai.saia_farmers");

			List<SaiaFarmData> msqlData = farmwriteResultSet(resultSet);
			//System.out.println("check point 3 all");


			//ArrayList<Comment> comment= new ArrayList<>();

			return msqlData;
		}}

	public List<SaiaFarmData> ETCheckFarmDataExist(String userId, String farmName) throws SQLException, ClassNotFoundException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {


			//resultSet = statement.executeQuery("select head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates from sai.saia_farmers");
			//resultSet = statement.executeQuery("select * from sai.saia_farmers");
			//String query = "select user_id, farm_name, farm_size, farm_type, farmsizecategory, farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE farmer_id =+userId";
			String query = "select user_id, farm_name, type,farm_id,farm_size, farm_type, farmsizecategory, address, date_created, date_modified,farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE user_id ='" + userId + "' AND farm_name='" + farmName + "'";

			//resultSet = statement.executeQuery("select user_id, farm_name, farm_size, farm_type, farmsizecategory, farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE farmer_id ="+farmer_id);
			resultSet = statement.executeQuery(query);




			//resultSet = statement.executeQuery("select * from sai.saia_farmers");

			List<SaiaFarmData> msqlData = farmwriteResultSet(resultSet);
			//System.out.println("check point 3 all");


			//ArrayList<Comment> comment= new ArrayList<>();

			return msqlData;
		}}


	public List<SaiaFarmData> SingleFarmData(int farm_id) throws SQLException, ClassNotFoundException, ParseException {


		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {
			System.out.println("check1 for single farmer");
			String query = "select user_id, farm_id, farmer_id, type, farm_name, farmsizecategory, farm_type, farm_size, address, ST_AsText(farm_coordinates,'axis-order=long-lat'), date_created, date_modified from sai.agri_farm WHERE farm_id ="+farm_id;
			resultSet = statement.executeQuery(query);
			System.out.println("check2 for single farmer");
			List<SaiaFarmData> msqlData = farmwriteResultSet(resultSet);
			System.out.println("checking for farm data------------------"+msqlData);
			return msqlData;
		}}

	public List<SaiaFarmData> FarmerFarmsData(String  userId) throws SQLException, ClassNotFoundException, ParseException {


		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {
			String query = "select user_id, farm_id, farmer_id, type, farm_name, farmsizecategory, farm_type, farm_size, address, ST_AsText(farm_coordinates,'axis-order=long-lat'), date_created, date_modified from sai.agri_farm WHERE user_id ='"+userId+"';";
			resultSet = statement.executeQuery(query);

			List<SaiaFarmData> msqlData = farmwriteResultSet(resultSet);
			System.out.println("checking for farm data------------------"+msqlData);
			return msqlData;
		}}

	public List<SaiaiParcelData> CheckParcelDataDuplicate(String userId, String parcelName) throws SQLException, ClassNotFoundException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		} else {
			System.out.println("check2 client parcel");
			//String query = "select parcel_id, user_id, farm_id, type, ST_AsText(location,'axis-order=long-lat'), area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, water_stressmean, parcel_name from sai.agri_parcel WHERE user_id ="+user_id;
			String query = "select parcel_id, user_id, farm_id, type, ST_AsText(location,'axis-order=long-lat'), area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, date_created, date_modified,water_stressmean, parcel_name from sai.agri_parcel WHERE user_id ='" + userId + "' AND parcel_name='" + parcelName + "'";
			System.out.println("check3 client parcel");
			this.resultSet = this.statement.executeQuery(query);
			List<SaiaiParcelData> msqlData = this.parcelwriteResultSet(this.resultSet);
			return msqlData;
		}
	}

	public List<SaiaiParcelData> ETCheckParcelDataExist(String userId, String parcelName) throws SQLException, ClassNotFoundException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {


			//resultSet = statement.executeQuery("select head_name, gender, son_wife_daughter, literacy, village, address_landmark, farmer_address_coordinates from sai.saia_farmers");
			//resultSet = statement.executeQuery("select * from sai.saia_farmers");
			//String query = "select user_id, farm_name, farm_size, farm_type, farmsizecategory, farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE farmer_id =+userId";
			String query = "select user_id, parcel_name, type,parcel_id,  area, category, related_source, belongs_to, has_agri_crop, has_agri_soil,last_planted_at, water_stressmean, date_created, date_modified,farm_id, ST_AsText(location,'axis-order=long-lat') from sai.agri_parcel WHERE user_id ='" + userId + "' AND parcel_name='" + parcelName + "'";
			//String query = "select user_id, farm_name, type,farm_id,farm_size, farm_type, farmsizecategory, address, date_created, date_modified,farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE user_id ='" + userId + "' AND farm_name='" + farmName + "'";


			//resultSet = statement.executeQuery("select user_id, farm_name, farm_size, farm_type, farmsizecategory, farmer_id, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE farmer_id ="+farmer_id);
			resultSet = statement.executeQuery(query);




			//resultSet = statement.executeQuery("select * from sai.saia_farmers");

			List<SaiaiParcelData> msqlData = parcelwriteResultSet(resultSet);
			//System.out.println("check point 3 all");


			//ArrayList<Comment> comment= new ArrayList<>();

			return msqlData;
		}}

	public List<SaiaiParcelData> parcelUpdateDB(Integer parcelId, String category, String hasAgriCrop, String lastPlantedAt, String parcelName) throws SQLException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		} else {

			String query1 = "UPDATE sai.agri_parcel\n" +
					"SET parcel_name ='"+parcelName+"'," +
					"last_planted_at ='"+lastPlantedAt+"'," +
					"category ='"+category+"'," +
					"has_agri_crop ='"+hasAgriCrop+
					"' WHERE parcel_id="+parcelId+"";
			this.statement.execute(query1);
			String query2 = "select parcel_id, user_id, farm_id, type, ST_AsText(location,'axis-order=long-lat'), area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, date_created, date_modified, water_stressmean, parcel_name from sai.agri_parcel WHERE parcel_id ='" + parcelId+"'";
			this.resultSet = this.statement.executeQuery(query2);

			List<SaiaiParcelData> msqlData = this.parcelwriteResultSet(this.resultSet);



			System.out.println("checking for parcel data------------------" + msqlData);
			return msqlData;
		}
	}
	public List<SaiaiParcelData> deleteParcel(Integer parcelID) throws SQLException, ClassNotFoundException, ParseException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		} else {
			String query2 = "select parcel_id, user_id, farm_id, type, ST_AsText(location,'axis-order=long-lat'), area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, date_created, date_modified, water_stressmean, parcel_name from sai.agri_parcel WHERE parcel_id =" + parcelID;
			String query1 = "delete from sai.agri_parcel WHERE parcel_id =" + parcelID;
			this.resultSet = this.statement.executeQuery(query2);
			List<SaiaiParcelData> msqlData = this.parcelwriteResultSet(this.resultSet);
			this.statement.execute(query1);


			System.out.println("checking for parcel data------------------" + msqlData);
			return msqlData;
		}
	}
	public List<SaiaiParcelData> SingleParcelData(int parcelId) throws SQLException, ClassNotFoundException, ParseException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		} else {
			String query = "select parcel_id, user_id, farm_id, type, ST_AsText(location,'axis-order=long-lat'), area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, date_created, date_modified, water_stressmean, parcel_name from sai.agri_parcel WHERE parcel_id =" + parcelId;
			this.resultSet = this.statement.executeQuery(query);

			List<SaiaiParcelData> msqlData = this.parcelwriteResultSet(this.resultSet);
			System.out.println("checking for parcel data------------------" + msqlData);
			return msqlData;
		}
	}
	public List<SaiaiParcelData> SingleParcelData_uid(String userId) throws SQLException, ClassNotFoundException, ParseException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		} else {
			String query = "select parcel_id, user_id, farm_id, type, ST_AsText(location,'axis-order=long-lat'), area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, date_created, date_modified, water_stressmean, parcel_name from sai.agri_parcel WHERE user_id ='" + userId+"'";
			this.resultSet = this.statement.executeQuery(query);

			List<SaiaiParcelData> msqlData = this.parcelwriteResultSet(this.resultSet);
			System.out.println("checking for parcel data------------------" + msqlData);
			return msqlData;
		}
	}
	
	private List<SaiaiParcelData> parcelwriteResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		List<SaiaiParcelData> parcelData = new ArrayList<>();
		boolean isParcelExist = false;
		while (resultSet.next()) {

			String parcelId =resultSet.getString("parcel_id");
			String userId =resultSet.getString("user_id");
			String farm_id =resultSet.getString("farm_id");
			String type = resultSet.getString("type");
			String location = resultSet.getString("ST_AsText(location,'axis-order=long-lat')");
			String area = resultSet.getString("area");
			String category = resultSet.getString("category");
			String relatedSource =resultSet.getString("related_source");
			String belongsTo = resultSet.getString("belongs_to");
			String hasAgriCrop = resultSet.getString("has_agri_crop");
			String hasAgriSoil = resultSet.getString("has_agri_soil");
			String lastPlantedAt = resultSet.getString("last_planted_at");
			String date_created = resultSet.getString("date_created");
			String date_modified = resultSet.getString("date_modified");
			String waterStressmean = resultSet.getString("water_stressmean");
			String parcelName = resultSet.getString("parcel_name");
			System.out.println("client side check4");
			//list.add(farm_name);

			/*SaiaiParcelData cData = new SaiaiParcelData(resultSet.getString("parcel_id"), resultSet.getString("user_id"), resultSet.getString("farm_id"),resultSet.getString("type"), location, resultSet.getString("area"), resultSet.getString("category"), resultSet.getString("related_source"), resultSet.getString("belongs_to"),
					 resultSet.getString("has_agri_crop"), resultSet.getString("has_agri_soil"), resultSet.getString("last_planted_at"), resultSet.getString("date_created"), resultSet.getString("date_modified"), resultSet.getString("water_stressmean"));*/

			SaiaiParcelData cData = new SaiaiParcelData(resultSet.getString("parcel_id"), resultSet.getString("user_id"), resultSet.getString("farm_id"),resultSet.getString("type"), location, resultSet.getString("area"), resultSet.getString("category"), resultSet.getString("related_source"), resultSet.getString("belongs_to"),
					resultSet.getString("has_agri_crop"), resultSet.getString("has_agri_soil"), resultSet.getString("last_planted_at"), resultSet.getString("date_created"), resultSet.getString("date_modified"), resultSet.getString("water_stressmean"), resultSet.getString("parcel_name"));
			System.out.println("client side check5");

			parcelData.add(cData);

			//list.add(resultSet.getString(2));

		}
		int size = list.size();
		if(size >=1)
		{
			isParcelExist = true;
			System.out.println("eixit parcel: "+list);

		}
		System.out.println("is parcel already exist: "+isParcelExist);
		return parcelData;

	}

	public List<SaiaiParcelData> parcelPostIntoDataBase(String userId, String farm_id, String location, String area, String category, String relatedSource, String belongsTo, String hasAgriCrop, String hasAgriSoil, String lastPlantedAt, String waterStressmean, String parcelName) throws SQLException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		} else {
			this.preparedStatement = this.connect.prepareStatement("insert into  sai.agri_parcel (user_id, farm_id, location, area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, water_stressmean, parcel_name) VALUES (?, ?, ST_GeomFromGeoJSON(?),? , ?, ?,  ?, ?, ?, ?,?,?)");
			//System.out.println("farmer_id," + farmer_id + "farmsizecategory," + farmsizecategory + " farm_name " + farm_name + " farm_type " + farm_type + " farm_size " + farm_size + " farm_coordinates " + farm_coordinates + " address " + address);
			this.preparedStatement.setString(1, userId);
			this.preparedStatement.setString(2, farm_id);
			this.preparedStatement.setString(3, location);
			this.preparedStatement.setString(4, area);
			this.preparedStatement.setString(5, category);
			this.preparedStatement.setString(6, relatedSource);
			this.preparedStatement.setString(7, belongsTo);
			this.preparedStatement.setString(8, hasAgriCrop);
			this.preparedStatement.setString(9, hasAgriSoil);
			this.preparedStatement.setString(10, lastPlantedAt);
			this.preparedStatement.setString(11, waterStressmean);
			this.preparedStatement.setString(12, parcelName);
			this.preparedStatement.executeUpdate();
			String query2 = "select parcel_id, user_id, farm_id, type, ST_AsText(location,'axis-order=long-lat'), area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, date_created, date_modified, water_stressmean, parcel_name from sai.agri_parcel ORDER BY parcel_id DESC LIMIT 1";
			this.resultSet = this.statement.executeQuery(query2);
			List<SaiaiParcelData> parcelPostData=this.parcelwriteResultSet(this.resultSet);
			return parcelPostData;
		}
	}

	public List<SaiaiParcelData> ParcelData() throws SQLException, ClassNotFoundException {
		boolean connectionValue = this.sqlconn();
		if (!connectionValue) {
			System.out.println("Exception: not connected properly");
			return null;
		} else {
			this.resultSet = this.statement.executeQuery("select parcel_id, user_id, farm_id, type, ST_AsText(location,'axis-order=long-lat'), area, category, related_source, belongs_to, has_agri_crop, has_agri_soil, last_planted_at, date_created, date_modified, water_stressmean, parcel_name from sai.agri_parcel");
			new ArrayList();
			List<SaiaiParcelData> retSariParcel = this.parcelwriteResultSet(this.resultSet);
			return retSariParcel;
		}
	}
///below to check duplicate farmer in farmer table
	public List<SaiaFarmerData> CheckDupFarmer(String user_id) throws SQLException, ClassNotFoundException, ParseException {

		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {

			//String query = "select farmer_id, type, head_name, gender, son_wife_daughter, literacy, address, ST_AsText(farmer_address_coordinates,'axis-order=long-lat'), date_created, date_modified from sai.saia_farmers WHERE farmer_id ="+farmer_id;
			//String query = "select farmer_id, type, head_name, gender, son_wife_daughter, literacy, address, ST_AsText(farmer_address_coordinates,'axis-order=long-lat'), date_created, date_modified from sai.saia_farmers WHERE user_id = +user_id";
			String query = "select farmer_id, user_id, type, head_name, gender, son_wife_daughter, literacy, address, ST_AsText(farmer_address_coordinates,'axis-order=long-lat'), date_created, date_modified from sai.saia_farmers WHERE user_id ='"+user_id+"';";
			resultSet = statement.executeQuery(query);

			List<SaiaFarmerData> msqlData = writeResultSet(resultSet);
			return msqlData;
		}}


	public List<SaiaFarmData> ETFarmsData(String  farmName) throws SQLException, ClassNotFoundException, ParseException {



		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {
			//List<Float> coord;
			String query = "select farm_name, ST_AsText(farm_coordinates,'axis-order=long-lat') from sai.agri_farm WHERE farm_name ='"+farmName+"';";
			resultSet = statement.executeQuery(query);


			List<SaiaFarmData> msqlData = etfarmwriteResultSet(resultSet);

			System.out.println("CHECKING FOR FARM ET data------------------"+msqlData);
			/*String coor = msqlData.get(0).getFarm_coordinates();
			System.out.println("coordinates: "+coor);
			String coord=coor.replaceAll("[^a-zA-Z0-9,.) ]","");
			String coordi=coord.replace("POLYGON","[");
			String coordin=coordi.replaceFirst("[)]","]");
			String coordina=coordin.replace(")","");
			String coordinate=coordina.replace(" ",", ");

			//String coordi=coord.replaceAll("[)]","]");
			System.out.println("Desired coordinates"+coordinate);*/



			//for (int i =0;i<msqlData.toArray().length;i=i+1)
			return msqlData;

		}
	}

	private List<SaiaFarmData> etfarmwriteResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		List<SaiaFarmData> farmData = new ArrayList<>();
		boolean isFarmsExist = false;
		while (resultSet.next()) {

			String farm_name = resultSet.getString("farm_name");

			String farm_coordinates = resultSet.getString("ST_AsText(farm_coordinates,'axis-order=long-lat')");

			SaiaFarmData cData = new SaiaFarmData(farm_coordinates, resultSet.getString("farm_name"));


			farmData.add(cData);



		}
		int size = list.size();
		if(size >=1)
		{
			isFarmsExist = true;
			System.out.println("eixit farm name: "+list);

		}
		System.out.println("is farm already exist: "+isFarmsExist);
		return farmData;

	}

	public List<SaiaiParcelData> ETParcelData(String  parcelName) throws SQLException, ClassNotFoundException, ParseException {



		boolean connectionValue = this.sqlconn();
		if(!connectionValue){
			System.out.println("Exception: not connected properly");
			return null;
		}
		else {
			//List<Float> coord;
			String query = "select parcel_name, ST_AsText(location,'axis-order=long-lat') from sai.agri_parcel WHERE parcel_name ='"+parcelName+"';";
			resultSet = statement.executeQuery(query);


			List<SaiaiParcelData> msqlData = etparcelwriteResultSet(resultSet);

			System.out.println("CHECKING FOR PARCEL ET data------------------"+msqlData);

			return msqlData;

		}
	}

	private List<SaiaiParcelData> etparcelwriteResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		List<String> list = new ArrayList<String>();
		List<SaiaiParcelData> parcelData = new ArrayList<>();
		boolean isParcelExist = false;
		while (resultSet.next()) {

			String parcelName = resultSet.getString("parcel_name");

			String location = resultSet.getString("ST_AsText(location,'axis-order=long-lat')");

			SaiaiParcelData cData = new SaiaiParcelData(location, resultSet.getString("parcel_name"));
			//SaiaiParcelData cData = new SaiaiParcelData(location, resultSet.getString("farm_name"));


			parcelData.add(cData);



		}
		int size = list.size();
		if(size >=1)
		{
			isParcelExist = true;
			System.out.println("eixit parcel name: "+list);

		}
		System.out.println("is parcel already exist: "+isParcelExist);
		return parcelData;

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
