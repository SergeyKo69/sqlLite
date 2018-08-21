package ru.macrohome.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ru.macrohome.common.Answers;
import ru.macrohome.common.Entities;
import ru.macrohome.common.Tables;
import ru.macrohome.entity.Contact;
import ru.macrohome.server.Connector;
import ru.macrohome.server.DataBaseUtility;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Controller {
    private ObservableList<Contact> tableData = FXCollections.observableArrayList();

    @FXML
    TextField txtName;
    @FXML
    TextField txtEmail;
    @FXML
    TableView tableContacts;
    @FXML
    TableColumn<Contact, Integer> id;
    @FXML
    TableColumn<Contact, String> name;
    @FXML
    TableColumn<Contact, String> email;
    @FXML
    Button btnAdd;
    @FXML
    Button btnDelete;

    @FXML
    private void initialize(){
        id.setCellValueFactory(new PropertyValueFactory<Contact, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<Contact, String>("email"));
        refreshFileTable();
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Connector.closeSessionFactory();
        Platform.exit();
    }
    private void refreshFileTable() {
        tableContacts.getItems().clear();
        List<Entities> list = DataBaseUtility.getList(Tables.Contacts);
        for (int i = 0; i < list.size(); i++) {
            tableData.add((Contact) list.get(i));
        }
         tableContacts.setItems(tableData);
    }

    public void showMessage(Alert.AlertType type, String title, String msg){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void ClickAdd(MouseEvent mouseEvent) {
        String name = txtName.getText();
        String email = txtEmail.getText();
        if (!name.equals("") & !email.equals("")){
            if (DataBaseUtility.save(new Contact(name,email)) == Answers.ERROR){
                showMessage(Alert.AlertType.ERROR,"Ошибка добавления","При добавлении в базу возникла ошибка!");
            }else{
                txtName.setText("");
                txtEmail.setText("");
                refreshFileTable();
            }
        }
    }

    public void ClickDelete(MouseEvent mouseEvent) {
        Contact rowContact = (Contact) tableContacts.getSelectionModel().getSelectedItem();
        if (rowContact != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удаление");
            alert.setHeaderText("Подтверждение удаления данных");
            alert.setContentText("Удалить запись ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (DataBaseUtility.delete(rowContact) == Answers.ERROR){
                    showMessage(Alert.AlertType.ERROR,"Ошибка удаления","Ошибка удаления записи");
                }else{
                    refreshFileTable();
                }
            }

        }
    }

    public void ClickTableContacts(MouseEvent mouseEvent) {
    }
}
