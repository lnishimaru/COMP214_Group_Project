package application;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainScreenController {

    @FXML
    private Button expiringButton;

    @FXML
    private TextField listingIdTxtField;

    @FXML
    private TextField statusResultTxtField;

    @FXML
    private Button agentDetailsButton;

    @FXML
    private TextField agentIdTxtField;

    @FXML
    private TextField agentDetailsResultTxtField;
    
    @FXML
    private Label messageLabel;

    @FXML
    private Button cancelAddAgentButton;

    @FXML
    private Button AddAgentButton;

    @FXML
    private TextField firstNameTxtField;

    @FXML
    private TextField phoneTxtField;

    @FXML
    private TextField lastNameTxtField;

    @FXML
    private TextField emailTxtField;

    @FXML
    private Button cancelUpdateButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private TextField firstNameUpdateTxtField;

    @FXML
    private TextField phoneUpdateTxtField;

    @FXML
    private TextField lastNameAgentUpdateTxtField;

    @FXML
    private TextField emailUpdateTxtField;

    @FXML
    private TextField agentIdUpdateTxtField;

    @FXML
    private Button SearchMaxPriceButton;

    @FXML
    private TextField maxPriceTxtField;

    @FXML
    private TextArea belowPriceTextArea;
    
    @FXML
    private Label resultLabel;

    //Functions Tab
    //Search Expiring date
    @FXML
    void ExpiringButtonClicked(ActionEvent event) {
    	boolean ok = true;
    	try {
    		Integer.parseInt(listingIdTxtField.getText());
		} catch (Exception e) {
			ok = false;
			messageLabel.setText("Please enter the Listing ID");
		}
    	if (ok) 
    	{
    		try {
    			Connection con = DataSource.getConnection();
    			
    			CallableStatement cStmt = con.prepareCall("{? = call F_CHECK_EXPIRY_DATE(?)}");
    			
        	    cStmt.setInt(2,Integer.parseInt(listingIdTxtField.getText()));
        	    cStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
        	    cStmt.execute();
        	    String result = cStmt.getString(1);
        	    
        	    statusResultTxtField.setText(result);
    		
    			con.close();

    		} catch (Exception e) {
    			System.out.println(e);
    		}
    	}
    	   	 
    }
    //Search Agent Details
    @FXML
    void agentDetailsButtonClicked(ActionEvent event) {
    	boolean ok = true;
    	try {
    		Integer.parseInt(agentIdTxtField.getText());
		} catch (Exception e) {
			ok = false;
			messageLabel.setText("Please enter the Agent ID");
		}
    	if (ok)
    	{	
    		try {
    			Connection con = DataSource.getConnection();
			
    			CallableStatement cStmt = con.prepareCall("{? = call F_PRINT_AGENT(?)}");
			
    			cStmt.setInt(2, Integer.parseInt(agentIdTxtField.getText()));
    			cStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
    			cStmt.execute();
    			String result = cStmt.getString(1);
    	    
    			agentDetailsResultTxtField.setText(result);
		
    			con.close();

    		} catch (Exception e) {
    			messageLabel.setText("Connection error, please try again");
    		}
    	}
    }

    //Procedures
    
    @FXML
    void CancelAddAgentClicked(ActionEvent event) {
    	firstNameTxtField.clear();
    	lastNameTxtField.clear();
    	phoneTxtField.clear();
    	emailTxtField.clear();  	
    }
   
    @FXML
    void addAgentButtonClicked(ActionEvent event) {
    	boolean ok = true;
    	if (firstNameTxtField.getText().isEmpty() ||
    		lastNameTxtField.getText().isEmpty()  ||
    		phoneTxtField.getText().isEmpty()     ||
    		emailTxtField.getText().isEmpty())
    	{
    		ok = false;
    		messageLabel.setText("All fields required for insert");
    	}
    	if (ok)
    	{
    	try {
    		Integer.parseInt(phoneTxtField.getText());
    	} catch (Exception e) {
			messageLabel.setText("Phone should have only numbers");
			ok = false;
		}
    	}
    	
    	if(ok) {
    		
    	
    		try {
    			Connection con = DataSource.getConnection();
			
    			CallableStatement cStmt = con.prepareCall("{call P_INSERT_AGENT(?,?,?,?)}");
			
    			cStmt.setString(1, firstNameTxtField.getText());
    			cStmt.setString(2, lastNameTxtField.getText());
    			cStmt.setInt(3, Integer.parseInt(phoneTxtField.getText()));
    			cStmt.setString(4, emailTxtField.getText());
    			
    			cStmt.execute();
    	    
    			messageLabel.setText("Agent added successfully");
		
    			con.close();

    		} catch (Exception e) {
    			messageLabel.setText("Error, please check the fields and try again");
    		}
    		
    	}
    	
    }

 
    @FXML
    void cancelUpdateButtonClicked(ActionEvent event) {
    	agentIdUpdateTxtField.clear();
    	firstNameUpdateTxtField.clear();
    	lastNameAgentUpdateTxtField.clear();
    	phoneUpdateTxtField.clear();
    	emailUpdateTxtField.clear();
    	messageLabel.setText("");
    }

    @FXML
    void updateButtonClicked(ActionEvent event) {
    	boolean ok = true;
    	if (agentIdUpdateTxtField.getText().isEmpty()       ||
    		firstNameUpdateTxtField.getText().isEmpty()     ||
    		lastNameAgentUpdateTxtField.getText().isEmpty() ||
    		phoneUpdateTxtField.getText().isEmpty()         ||
    		emailUpdateTxtField.getText().isEmpty())
    	{
    		ok = false;
    		messageLabel.setText("All fields required for update");
    	}
    	if (ok)
    	{
    	try {
    		Integer.parseInt(phoneUpdateTxtField.getText());
    	} catch (Exception e) {
			messageLabel.setText("Invalid Phone Number");
			ok = false;
		}
    	}
    	
    	if(ok) {
    		
    	
    		try {
    			Connection con = DataSource.getConnection();
			
    			CallableStatement cStmt = con.prepareCall("{call P_UPDATE_AGENT(?,?,?,?,?)}");
			
    			cStmt.setInt(1, Integer.parseInt(agentIdUpdateTxtField.getText()));
    			cStmt.setString(2, firstNameUpdateTxtField.getText());
    			cStmt.setString(3, lastNameAgentUpdateTxtField.getText());
    			cStmt.setInt(4, Integer.parseInt(phoneUpdateTxtField.getText()));
    			cStmt.setString(5, emailUpdateTxtField.getText());
    			
    			cStmt.execute();
    	    
    			messageLabel.setText("Agent updated successfully");
		
    			con.close();

    		} catch (Exception e) {
    			messageLabel.setText("Error, please check the fields and try again");
    		}
    		
    	}
    }
    
    @FXML
    void clearPriceSearchButton(ActionEvent event) {
    	maxPriceTxtField.clear();
    	belowPriceTextArea.setText(" ");
    	messageLabel.setText("");
    }

    @SuppressWarnings("null")
	@FXML
    void searchMaPriceClicked(ActionEvent event) {
    	

    	boolean ok = true;
    	if (maxPriceTxtField.getText().isEmpty())
    	{
    		ok = false;
    		messageLabel.setText("Please inform a Maximum Price");
    	}
    	if (ok)
    	{
    	try {
    		Integer.parseInt(maxPriceTxtField.getText());
    	} catch (Exception e) {
			messageLabel.setText("Invalid Price");
			ok = false;
		}
    	}
    	
    	if(ok) {
    		
    	
    		try {
    			Connection con = DataSource.getConnection();
    			CallableStatement cStmt = con.prepareCall("{call P_LIST_BELLOW_PRICE(?,?)}");
    			cStmt.setInt(1, Integer.parseInt(maxPriceTxtField.getText()));
    		
    			cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
    			ResultSet result = null;
    			String textArea = "Listing Id \t Property Id \t Agent Id \t\t Expiry Date \t Registry Date \t Transaction Type \t Price";
    			cStmt.execute();

    			    result = (ResultSet)cStmt.getObject(2);
    			    while(result.next()) 
    			    {
    			    	int ListingId = result.getInt(1);
    			    	int PropertyId = result.getInt(2);
    			    	int AgentId = result.getInt(3);
    			    	String ExpiryDate = result.getString(4);	
    			    	String RegistryDate = result.getString(5);
    			    	String TransactionType = result.getString(6);
    			    	double Price = result.getDouble(7);
    			    	textArea = textArea + "\n" + ListingId + "\t\t\t" + PropertyId + "\t\t\t" + AgentId + "\t\t" + ExpiryDate.substring(0, 10) + "\t" + RegistryDate.substring(0, 10) + "\t\t\t" + TransactionType + "\t\t" + String.format("%.2f",Price); 
    			    }
    			    
    			belowPriceTextArea.setText(textArea);
    			messageLabel.setText("");
    			con.close();

    		} catch (Exception e) {
    			messageLabel.setText("Error, please check the fields and try again");
    	    	System.out.print(e);
    		}
    		
    	}
    }
    @FXML
    void ExitMenuClicked(ActionEvent event) {
    	 Platform.exit();
    }

}
