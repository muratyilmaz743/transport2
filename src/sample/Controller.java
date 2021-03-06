package sample;

import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class Controller implements Initializable{
    public Button HAT_ID_SHOW;
    public TextField HAT_INPUT;
    public Button HAT_ID_RESET;
    public Label shoAd;
    public Label ShoSayi;
    public Label shoJunior;
    public Label shoFull;
    public TextField HAT_INPUT2;
    public Button HAT_ID_RESET2;
    public Button HAT_ID_SHOW2;
    public TableView Hat_Show_Table;
    public TextField HAT_SwInput;
    public Button HAT_ID_RESET1;
    public Button HAT_SHOW;
    public Label info_label;
    public Button card_show;
    public Button HAT_ID_RESET11;
    public TextField Card_input;
    public Label showbalance;
    public Label showLastdate;
    public Label showType;
    public Button message_btn;
    public Button HAT_ID_RESET_message;
    public TextField message_input;
    public TextArea messageText;
    public Label messageWarning2;
    public Label messageWarning;
    public Button HAT_ID_SHOW3;
    public Button HAT_ID_RESET3;
    public TextField HAT_INPUT3;
    public Label günInfo;
    public Label saatInfo;
    public TableView tableHour;
    public CheckBox çarşamba;
    public CheckBox pazartesi;
    public CheckBox cuma;
    public CheckBox cumartesi;
    public CheckBox pazar;
    public CheckBox salı;
    public CheckBox perşembe;



    //Connection for database//
    Connection conn = null;
    Statement statement = null;
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/transportation?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String user = "root";
    String pass = "1234";
    private ObservableList<ObservableList> data;

    @FXML
    private TableView tableview;
                                                        /********************
                                                         *FİRST TAB AND TABLE
                                                        ********************/
    private ObservableList<ObservableList> hatdata;
    private ObservableList<String> hatdata2;

    {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(url, user, pass);
            statement = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void Hat_Show(){

        try {
            String hat_id = HAT_INPUT.getText();
            String sql = "select HAT_KİSAAD,durak_sayi,hat_ücreti_junior,hat_ücreti_senior from transportation.hat where hat_ID="+hat_id;
            ResultSet rs = statement.executeQuery(sql);



            while (rs.next()) {
                String adi = (String) rs.getObject(1);
                int durakSayi = (int) rs.getObject(2);
                float genc = (float) rs.getObject(3);
                float yetiskin = (float) rs.getObject(4);

                shoAd.setText(adi);
                ShoSayi.setText(String.valueOf(durakSayi));
                shoFull.setText(String.valueOf(yetiskin));
                shoJunior.setText(String.valueOf(genc));


            }
        }catch (SQLException e) {
            e.printStackTrace();
        }


        HAT_INPUT.setText("");
        HAT_INPUT.requestFocus();
    }

                                                                /*********************************
                                                                 *SECOND TAB CONNECTİON AND TABLE
                                                                *********************************/

    public void Hat_Show2(){

        String hat_id = HAT_INPUT2.getText();
        String[] columns = {"Sıra","No","Adı"};
        data = FXCollections.observableArrayList();



        try{

            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT durak_sirasi.durak_sira , durak_sirasi.durak_id ,durak.Durak_adi from durak_sirasi INNER JOIN durak on durak_sirasi.durak_id = durak.Durak_ID where durak_sirasi.hat_id="+hat_id +" ORDER BY durak_sirasi.durak_sira";
            //ResultSet
            ResultSet rst = conn.createStatement().executeQuery(SQL);

            /**
             * cleaning before post
             */
            for(int i=0 ; i<rst.getMetaData().getColumnCount(); i++)
                tableview.getColumns().clear();

            for(int i=0 ; i<rst.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(columns[i]);
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tableview.getColumns().addAll(col);
            }
            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rst.next()){
                //Iterate Row

                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rst.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rst.getString(i));

                }
                data.add(row);
            }
            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

                                                                    /********************
                                                                    *THİRD TAB AND TABLE
                                                                    **********************/

    public void Hat_Show3(){

        String durak_id = HAT_SwInput.getText();
        String[] columns = {"HAT NUMARASI","HAT ADI"};
        hatdata = FXCollections.observableArrayList();

        try{
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT  durak_sirasi.hat_id,hat.HAT_KİSAAD from durak_sirasi INNER JOIN hat on durak_sirasi.hat_id = hat.hat_ID where durak_id="+durak_id;
            ResultSet rst = conn.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rst.getMetaData().getColumnCount(); i++)
                Hat_Show_Table.getColumns().clear();

            info_label.setText(durak_id+" numaralı duraktan geçen hatlar.");

            for(int i=0 ; i<rst.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(columns[i]);
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param
                        -> new SimpleStringProperty(param.getValue().get(j).toString()));

                Hat_Show_Table.getColumns().addAll(col);
            }
            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rst.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rst.getMetaData().getColumnCount(); i++){
                    row.add(rst.getString(i));
                }
                hatdata.add(row);
            }
            Hat_Show_Table.setItems(hatdata);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }


                                                                            /********************
                                                                            *FİRST TAB AND TABLE
                                                                            ********************/

     public void Card_show(){

         try {
             String card_no = Card_input.getText();
             String sql = "select card_type,balance,Last_date from transportation.travel_card where card_number="+card_no;
             ResultSet rs = statement.executeQuery(sql);



             while (rs.next()) {
                 String type = (String) rs.getObject(1);
                 String balance = (String) rs.getObject(2);
                 Date date = (Date) rs.getObject(3);


                 showType.setText(type);
                 showbalance.setText(balance);
                 showLastdate.setText(String.valueOf(date));



             }
         }catch (SQLException e) {
             e.printStackTrace();
         }


         Card_input.setText("");
         Card_input.requestFocus();
     }
    public void message_sent(){
         if (message_input.getText().equals(""))
             messageWarning.setText("Kişi numaranızı bilmek zorundayım :(");
         if (messageText.getText()=="")
             messageWarning2.setText("");

        try {
            String SQL = "INSERT INTO transportation.mmessage (`person_passenger_id`, `message_`) values ('"+message_input.getText()+"','"+messageText.getText()+"')";
            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("adding error");
        }


    }

    public void Hat_Show4(){
        pazartesi.setSelected(false);
        salı.setSelected(false);
        çarşamba.setSelected(false);
        perşembe.setSelected(false);
        cuma.setSelected(false);
        cumartesi.setSelected(false);
        pazar.setSelected(false);

        String hatNo = HAT_INPUT3.getText();
        String[] columns1 = {"TIME"};
        hatdata = FXCollections.observableArrayList();
        hatdata2 = FXCollections.observableArrayList();

        try{
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL  = "SELECT gün from gunler where hat_ID="+hatNo;
            String SQL2 = "SELECT saat from zamanlama where hat_ID="+hatNo;
            ResultSet rst = conn.createStatement().executeQuery(SQL);
            ResultSet rst2 = conn.createStatement().executeQuery(SQL2);

            for(int i=0 ; i<rst2.getMetaData().getColumnCount(); i++)
                tableHour.getColumns().clear();


            for(int i=0 ; i<rst2.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(columns1[i]);
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param
                        -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tableHour.getColumns().addAll(col);
            }



            while(rst2.next()){
                //Iterate Row

                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rst2.getMetaData().getColumnCount(); i++){

                    row.add(rst2.getString(i));
                }
                hatdata.add(row);
            }

            while(rst.next()){
                //Iterate Row

                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rst.getMetaData().getColumnCount(); i++){

                    row.add(rst.getString(i));
                }
                hatdata2.add(String.valueOf(row));
            }
            //FINALLY ADDED TO TableView
            tableHour.setItems(hatdata);

            for(int i=0 ; i<hatdata2.size(); i++){
                List<String> gunler = hatdata2.stream().collect(Collectors.toList());


                pazartesi.setSelected(gunler.contains("[pazartesi]"));
                salı.setSelected(gunler.contains("[salı]"));
                çarşamba.setSelected(gunler.contains("[çarşamba]"));
                perşembe.setSelected(gunler.contains("[perşembe]"));
                cuma.setSelected(gunler.contains("[cuma]"));
                cumartesi.setSelected(gunler.contains("[cumartesi]"));
                pazar.setSelected(gunler.contains("[pazar]"));

            }


        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }


     }


    public void Hat_Reset(){
        HAT_INPUT.setText("");
        HAT_INPUT2.setText("");
        HAT_SwInput.setText("");
        message_input.setText("");
        message_input.setText("");
        messageText.setText("");
        HAT_INPUT.requestFocus();
        HAT_INPUT2.requestFocus();
        HAT_SwInput.requestFocus();
        HAT_INPUT3.setText("");
        HAT_INPUT3.requestFocus();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
