package net.webHMI.pli.server;

//test

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigDecimal;


import net.webHMI.pli.client.PLIData;
import net.webHMI.pli.client.PLIService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */

public class PLIServiceImpl extends RemoteServiceServlet implements
PLIService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ConnectPipeIDDB cpidb;
	//private Connection consqlserver;
	private ConnectVantageDB cpvdb;
	private Connection convantage;
	private String jobnum;

	private String companyList = new String();
	private String partnumList = new String();
	private String jobnumList = new String();
	private int assemblyseqList ;
	private int mtlseqList ;
	private int packnumList ;
	private int packlineList ;
	private int vendornumList ;
	private String purpointList = new String();
	private String packslipList = new String();
	private int packsliplineList ;
	private String snprefixList = new String();
	private String snformatList = new String();
	private String snbasestructureList = new String();
	private String snbasenumberList = new String();
	private String scrapreasoncodeList = new String();
	private String snreferenceList = new String();
	private int rmanumList ;
	private int rmalineList ;
	private int rmareceiptList ;
	private int dmrnumList ;
	private String warehousecodeList = new String();
	private String binnumList = new String();
	private int contractnumList ;
	private int contractlineList ;
	private int nonconfnumList ;
	private BigDecimal number01List;
	private BigDecimal number02List;
	private BigDecimal number03List;
	private BigDecimal number04List ;
	private BigDecimal number05List ;
	private String date01List = new String();
	private String date02List = new String();
	private String date03List = new String();
	private String date04List = new String();
	private String date05List = new String();
	private String checkbox01List = new String();
	private String checkbox02List = new String();
	private String checkbox03List = new String();
	private String checkbox04List = new String();
	private String checkbox05List = new String();
	private String contexpiredateList = new String();
	private String warrexpirationList = new String();
	private int custnumList  ;
	private String shiptonumList = new String();
	private int tfpacknumList ;
	private int tfpacklineList ;
	private int ordernumList ;
	private int orderlineList ;
	private int orderrelnumList ;
	private int trannumList;


	public PLIServiceImpl(){

	//	cpidb = new ConnectPipeIDDB();
	//	consqlserver = null;
	//	consqlserver = cpidb.connectToDB();

		cpvdb = new ConnectVantageDB();
		convantage = cpvdb.connectToDB();
	}
	public PLIData getData(String pipenr) {
		PLIData ctr = new PLIData();




		//		try 
		//		{
		//			
		//			Statement st = consqlserver.createStatement();
		//			ResultSet rs = st.executeQuery("SELECT * FROM INVENTORY WHERE " +
		//					"PipeNo = '" +
		//					pipenr +
		//					"'");
		//			
		//			while ( rs.next() )
		//			{
		//				//ctr.setPipeno(rs.getString("PipeNo"));
		//				//ctr.setCustomer(rs.getString("Customer"));
		//				
		//				
		//				
		//				//ctr.setGrade(rs.getString("Grade"));
		//			}
		//			rs.close();
		//			st.close();
		//			
		//		}
		//		catch (SQLException se) {
		//
		//			System.err.println("getData()" + se.getMessage());
		//			consqlserver = cpidb.connectToDB();
		//		}



		try 
		{

			Statement st = convantage.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM serialno WHERE " +
					"serialnumber = '" +
					pipenr +
					"'");
			if (!rs.isBeforeFirst() ) {    
				System.out.println("PLIService GetData() No data"); 
				ctr.setStatus(0);
			} 
			while ( rs.next() )
			{
				ctr.setStatus(1);
				ctr.setPipeno(rs.getString("serialnumber"));
				ctr.setPartNumber(rs.getString("partnum"));
				jobnum = rs.getString("jobnum");
				ctr.setJobnumber(rs.getString("jobnum"));
				ctr.setCurrentWarehouse(rs.getString("warehousecode"));
				ctr.setOd(rs.getDouble("number03"));
				ctr.setGauge(rs.getDouble("number04"));
				ctr.setWeight(rs.getDouble("number02"));
				ctr.setLength(rs.getDouble("number01"));
				ctr.setCurrentBin(rs.getString("binnum"));
			}
			rs.close();
			st.close();

		}
		catch (SQLException se) {

			System.err.println("getData()" + se.getMessage());
			convantage = cpvdb.connectToDB();
		}
		if (ctr.getStatus()==1){
			try 
			{

				Statement st = convantage.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM jobasmbl WHERE " +
						"jobnum like '%" +
						jobnum +
						"'");

				while ( rs.next() )
				{
					ctr.setDescription(rs.getString("description"));

				}
				rs.close();
				st.close();

			}
			catch (SQLException se) {

				System.err.println("getData()" + se.getMessage());
				convantage = cpvdb.connectToDB();
			}
		}


		return ctr;
	}

	//	public String getUser(){
	//		Principal principal = getThreadLocalRequest().getUserPrincipal();
	//		  String name = principal.getName();
	//		  System.out.println("USer = " + name);
	//		return name;
	//	}

	private void updateSerialNo(String pipenr, String binnum, String warehousecode) {
		try {
			System.out.println("Connecting to database");
			System.out.println("pipenr = "+ pipenr + " binnum = " + binnum + " warehouse = " + warehousecode);

			String query = "UPDATE serialno " +
					"SET warehousecode = ?, binnum = ?, modifiedby = ?, modifieddate = ? " + 
					"WHERE serialnumber = ?" ;


			String modifieddate = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss.SSS").format(new Date());

			PreparedStatement ps = convantage.prepareStatement(query);
			ps.setString(1, warehousecode);
			ps.setString(2, binnum);
			ps.setString(3, "scanners");
			ps.setString(4, modifieddate);
			ps.setString(5, pipenr);
			ps.executeUpdate();


			System.out.println("Updated serialno");	
			ps.close();
		} catch (SQLException e) {
			System.out.println("Failed update [updateSerialNo()]");
		}
	}

	private void querySerialNo(String pipenr) {


		try {


			Statement st = convantage.createStatement();


			String query = "SELECT * FROM serialno WHERE serialnumber = '" + pipenr + "'";
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {

				companyList = rs.getString("company");
				partnumList = rs.getString("partnum");
				jobnumList = rs.getString("jobnum");
				assemblyseqList = rs.getInt("assemblyseq");
				mtlseqList = rs.getInt("mtlseq");
				packnumList = rs.getInt("packnum");
				packlineList = rs.getInt("packline");
				vendornumList = rs.getInt("vendornum");
				purpointList = rs.getString("purpoint");
				packslipList = rs.getString("packslip");
				packsliplineList = rs.getInt("packslipline");
				snprefixList = rs.getString("snprefix");
				snformatList = rs.getString("snformat");
				snbasestructureList = rs.getString("snbasestructure");
				snbasenumberList = rs.getString("snbasenumber");
				scrapreasoncodeList = rs.getString("scrapreasoncode");
				snreferenceList = rs.getString("snreference");
				rmanumList = rs.getInt("rmanum");
				rmalineList = rs.getInt("rmaline");
				rmareceiptList = rs.getInt("rmareceipt");
				dmrnumList = rs.getInt("dmrnum");
				warehousecodeList = rs.getString("warehousecode");
				binnumList = rs.getString("binnum");
				contractnumList = rs.getInt("contractnum");
				contractlineList = rs.getInt("contractline");
				nonconfnumList = rs.getInt("nonconfnum");
				number01List = new BigDecimal(rs.getString("number01"));
				number02List = new BigDecimal(rs.getString("number02"));
				number03List = new BigDecimal(rs.getString("number03"));
				number04List = new BigDecimal(rs.getString("number04"));
				number05List = new BigDecimal(rs.getString("number05"));
				date01List = rs.getString("date01");
				date02List = rs.getString("date02");
				date03List = rs.getString("date03");
				date04List = rs.getString("date04");
				date05List = rs.getString("date05");
				checkbox01List = rs.getString("checkbox01");
				checkbox02List = rs.getString("checkbox02");
				checkbox03List = rs.getString("checkbox03");
				checkbox04List = rs.getString("checkbox04");
				checkbox05List = rs.getString("checkbox05");
				contexpiredateList = rs.getString("contexpiredate");
				warrexpirationList = rs.getString("warrexpiration");
				custnumList = rs.getInt("custnum");
				shiptonumList = rs.getString("shiptonum");
				tfpacknumList = rs.getInt("tfpacknum");
				tfpacklineList = rs.getInt("tfpackline");
				ordernumList = rs.getInt("ordernum");
				orderlineList = rs.getInt("orderline");
				orderrelnumList = rs.getInt("orderrelnum");
			}
			rs.close();
			st.close();
			System.out.println("Queried");


		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Failed update [querySerialNo()]");
		}
	}

	private void getTranNum(String pipenr) {

		try {
			trannumList=0;
			Statement st = convantage.createStatement();



			String query = "SELECT MAX(trannum) as trannum FROM sntran WHERE serialnumber = '" + pipenr + "'";
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {

				int temptran = 0;
				if(rs.wasNull()) {
					temptran = 1;
				} else {
					temptran = rs.getInt("trannum");
					temptran ++;
					trannumList = temptran;
				}
			}

			rs.close();
			st.close();
			System.out.println("Transaction number get");


		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Failed update [getTranNum()]");
		}
	}

	private void insertSntran(String pipenr) {
		try {

			String trandate = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss.SSS").format(new Date());
			String sysdate_ = trandate;				

			String query = "INSERT INTO sntran " +
					"(company, partnum, serialnumber, trannum, contractnum, contractline, packnum, packline, rmanum, rmaline, entryperson, trandate, jobnum, assemblyseq, " +
					"mtlseq, vendornum, purpoint, packslip, packslipline, snprefix, snformat, snbasestructure, snbasenumber, scrapreasoncode, snreference, rmareceipt, " +
					"dmrnum, sysdate_, warehousecode, binnum, contexpiredate, warrexpiration, custnum, shiptonum, nonconfnum, tfpacknum, tfpackline, number01, number02, " + 
					"number03, number04, number05, date01, date02, date03, date04, date05, checkbox01, checkbox02, checkbox03, checkbox04, checkbox05, " + 
					"ordernum, orderline, orderrelnum, trantype) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"?, ?, ?, ?, ?,?)";
			PreparedStatement ps = convantage.prepareStatement(query);

			ps.setString(1, companyList);
			ps.setString(2, partnumList);
			ps.setString(3, pipenr);
			ps.setInt(4, trannumList);
			ps.setInt(5, contractnumList);
			ps.setInt(6, contractlineList);
			ps.setInt(7, packnumList);
			ps.setInt(8, packlineList);
			ps.setInt(9, rmanumList);
			ps.setInt(10, rmalineList);
			ps.setString(11, "scanner");
			ps.setString(12, trandate);
			ps.setString(13, jobnumList);
			ps.setInt(14, assemblyseqList);
			ps.setInt(15, mtlseqList);
			ps.setInt(16, vendornumList);
			ps.setString(17, purpointList);
			ps.setString(18, packslipList);
			ps.setInt(19, packsliplineList);
			ps.setString(20, snprefixList);
			ps.setString(21, snformatList);
			ps.setString(22, snbasestructureList);
			ps.setString(23, snbasenumberList);
			ps.setString(24, scrapreasoncodeList);
			ps.setString(25, snreferenceList);
			ps.setInt(26, rmareceiptList);
			ps.setInt(27, dmrnumList);
			ps.setString(28, sysdate_);
			ps.setString(29, warehousecodeList);
			ps.setString(30, binnumList);
			ps.setString(31, contexpiredateList);
			ps.setString(32, warrexpirationList);
			ps.setInt(33, custnumList);
			ps.setString(34, shiptonumList);
			ps.setInt(35, nonconfnumList);
			ps.setInt(36, tfpacknumList);
			ps.setInt(37, tfpacklineList);
			ps.setBigDecimal(38, number01List);
			ps.setBigDecimal(39, number02List);
			ps.setBigDecimal(40, number03List);
			ps.setBigDecimal(41, number04List);
			ps.setBigDecimal(42, number05List);
			ps.setString(43, date01List);
			ps.setString(44, date02List);
			ps.setString(45, date03List);
			ps.setString(46, date04List);
			ps.setString(47, date05List);
			ps.setString(48, checkbox01List);
			ps.setString(49, checkbox02List);
			ps.setString(50, checkbox03List);
			ps.setString(51, checkbox04List);
			ps.setString(52, checkbox05List);
			ps.setInt(53, ordernumList);
			ps.setInt(54, orderlineList);
			ps.setInt(55, orderrelnumList);
			ps.setString(56, "TRANSFER");

			ps.executeUpdate();

			System.out.println("INSERTED into sntran");
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed update");
		}
	}

	public void updateDB(String pipenr,String binnum, String warehousecode){

		updateSerialNo(pipenr,binnum,warehousecode);
		querySerialNo(pipenr);
		getTranNum(pipenr);
		insertSntran(pipenr);
	}


}
