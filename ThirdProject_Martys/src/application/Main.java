package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main extends Application {
	String selectedLocation;
	Martyr temp;

	public void start(Stage primaryStage) {
		primaryStage.setTitle("ASH-SHAHEED");
		DoubleLinkedList locations = new DoubleLinkedList();
		Stage tabsStage = new Stage();
		StackPane st = new StackPane();
		Scene scene = new Scene(st, 650, 400);
		backGround(st);
		Label title = new Label("ASH-SHAHEED");
		title.setTextFill(Color.WHEAT);
		title.setFont(new Font("Arial", 70));
		Button upload = new Button("Upload file");
		upload.setFont(new Font("Arial", 20));
		upload.setStyle(
				"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
		upload.setOnAction(e -> {
			int counter = 0;
			try {
				// reading the data from the file
				FileChooser fileChooser = new FileChooser();
				Stage fileChooserStage = new Stage();
				File file = fileChooser.showOpenDialog(fileChooserStage);
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				ArrayList<String> readFile = new ArrayList<>();
				while ((line = br.readLine()) != null) {
					readFile.add(line);

				}
				// addind the data into the lists
				String[] oneLine = new String[5];
				for (int i = 0; i < readFile.size() - 1; i++) {
					oneLine = readFile.get(i + 1).split(",");
					ArrayList<String> onelineL = new ArrayList<String>(Arrays.asList(oneLine));
					for (int j = 0; j < 1; j++) {
						if (onelineL.get(1).equals("")) {
							counter++;
						} else {
							if (locations.search(onelineL.get(2))) {
								int age = (int) Integer.parseInt(onelineL.get(1));
								Date date = new SimpleDateFormat("dd/MM/yyyy").parse(onelineL.get(3));
								char gender = onelineL.get(4).charAt(0);
								locations.getNode(onelineL.get(2)).location.getNamesAVL()
										.insertByName(new Martyr(onelineL.get(0), age, date, gender));
								if (locations.getNode(onelineL.get(2)).location.getDatesAVL()
										.findByDate(date) != null) {
									locations.getNode(onelineL.get(2)).location.getDatesAVL().findByDate(date).data
											.getStack().push(new Martyr(onelineL.get(0), age, date, gender));
								} else {

									locations.getNode(onelineL.get(2)).location.getDatesAVL()
											.insertByDate(new DateStack(date, new Stack()));
									locations.getNode(onelineL.get(2)).location.getDatesAVL().findByDate(date).data
											.getStack().push(new Martyr(onelineL.get(0), age, date, gender));
								}
							} else if (!(locations.search(onelineL.get(2)))) {
								String addlocation = onelineL.get(2);
								int age = (int) Integer.parseInt(onelineL.get(1));
								Date date = new SimpleDateFormat("dd/MM/yyyy").parse(onelineL.get(3));
								char gender = onelineL.get(4).charAt(0);
								Location loc = new Location(addlocation, new AVL<Martyr>(), new AVL<DateStack>());
								locations.addFirst(loc);
								locations.getNode(loc.getLocation()).location.getNamesAVL()
										.insertByName(new Martyr(onelineL.get(0).replaceAll("'", ""), age, date, gender));
								if (locations.getNode(loc.getLocation()).location.getDatesAVL()
										.findByDate(date) != null) {
									locations.getNode(onelineL.get(2)).location.getDatesAVL().findByDate(date).data
											.getStack().push(new Martyr(onelineL.get(0), age, date, gender));
								} else {

									locations.getNode(onelineL.get(2)).location.getDatesAVL()
											.insertByDate(new DateStack(date, new Stack()));
									locations.getNode(onelineL.get(2)).location.getDatesAVL().findByDate(date).data
											.getStack().push(new Martyr(onelineL.get(0), age, date, gender));
								}

							}
							// will use this counter to find the avg of martyr in a specific location

						}
					}
				}
				// call the sorting methods to sort the two linked list
				locations.sort();
				locations.PrintList();
				System.out.println(locations.count);
			} catch (FileNotFoundException e1) {
				dialog(AlertType.ERROR, "File not found");
			} catch (IOException e1) {
			} catch (ParseException e1) {
			}
			dialog(AlertType.WARNING, "The data has been uploaded successfully, BUT there was " + counter
					+ " martyr data ignored due to a lack of it");

			primaryStage.close();
			tabsStage.show();
		});
		VBox vb = new VBox(50);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(title, upload);
		st.getChildren().add(vb);
		primaryStage.setScene(scene);
		primaryStage.show();
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Location");
		Tab tab2 = new Tab("Martyrs");
		Tab tab3 = new Tab("Statistics");
		Tab tab4 = new Tab("Save");
		///////////////////////////////////////////////////////////////////////////////////////////////
		// the first Location tab that allows to add update delete or search for a
		// location
		{
			BorderPane tab1BP = new BorderPane();
			tab1.setContent(tab1BP);
			backGround(tab1BP);
			Button insert1 = new Button("Insert new location.");
			insert1.setMaxWidth(400);
			insert1.setFont(new Font("Arial", 15));
			insert1.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			// an action handler to insert a new location into the list after checking if it
			// does not exist
			insert1.setOnAction(e -> {
				TextField tf = new TextField("insert the new location here...");
				tf.setMaxWidth(300);
				tf.setMinHeight(50);
				Button insertBT = new Button("Insert");
				insertBT.setFont(new Font("Arial", 20));
				insertBT.setStyle(
						"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
				insertBT.setOnAction(m -> {
					Location l = new Location(tf.getText(), new AVL<Martyr>(), new AVL<DateStack>());
					if (locations.search(l.getLocation())) {
						dialog(AlertType.ERROR, "Sorry, the location you are trying to add already exists!");

					} else {
						locations.addFirst(l);
						// sort the linked list after adding a new location
						locations.sort();
						dialog(AlertType.INFORMATION, "The location have been added successfully.");
					}

				});
				VBox insertVB = new VBox();
				insertVB.setSpacing(10);
				insertVB.getChildren().addAll(tf, insertBT);
				insertVB.setAlignment(Pos.CENTER);
				tab1BP.setCenter(insertVB);
			});
			Button update1 = new Button("Update a pre-existing location.");
			update1.setMaxWidth(400);
			update1.setFont(new Font("Arial", 15));
			update1.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			// an action handler to update an existing location after checking if it does
			// exist
			update1.setOnAction(e -> {
				ArrayList<String> loc = new ArrayList<>();
				DoubleNode cur = locations.getfirst();
				int j = 0;
				while (j < locations.count) {
					loc.add(locations.getNode(cur.location.getLocation()).location.getLocation());
					cur = cur.next;
					j++;
				}
				ComboBox<String> comb = new ComboBox<>();
				for (int i = 0; i < loc.size(); i++) {
					comb.getItems().add(loc.get(i));
				}
				comb.setOnAction(m -> {
					for (int i = 0; i < loc.size(); i++) {
						comb.getItems().add(loc.get(i));

					}
				});
				TextField newTF = new TextField("insert the new location here...");
				newTF.setMaxWidth(300);
				newTF.setMinHeight(50);
				Button updateBT = new Button("Update");
				updateBT.setFont(new Font("Arial", 20));
				updateBT.setStyle(
						"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
				updateBT.setOnAction(m -> {
					if (locations.search(comb.getValue())) {
						locations.update(locations.getNode(comb.getValue()), newTF.getText());
						// sort the linked list after updating the location
						locations.sort();
						dialog(AlertType.INFORMATION, "The location have been updated successfully.");
					} else {
						dialog(AlertType.ERROR, "Sorry, the location you are trying to update does not exist!");
					}
				});
				VBox updateVB = new VBox();
				updateVB.setSpacing(10);
				updateVB.getChildren().addAll(comb, newTF, updateBT);
				updateVB.setAlignment(Pos.CENTER);
				tab1BP.setCenter(updateVB);
			});

			Button delete1 = new Button("Delete a pre-existing location.");
			delete1.setMaxWidth(400);
			delete1.setFont(new Font("Arial", 15));
			delete1.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			// this action to delete an existing location
			delete1.setOnAction(e -> {
				ArrayList<String> loc = new ArrayList<>();
				DoubleNode cur = locations.getfirst();
				int j = 0;
				while (j < locations.count) {
					loc.add(locations.getNode(cur.location.getLocation()).location.getLocation());
					cur = cur.next;
					j++;
				}
				ComboBox<String> comb = new ComboBox<>();
				for (int i = 0; i < loc.size(); i++) {
					comb.getItems().add(loc.get(i));
				}
				comb.setOnAction(m -> {
					for (int i = 0; i < loc.size(); i++) {
						comb.getItems().add(loc.get(i));

					}
				});
				Button deleteBT = new Button("Delete");
				deleteBT.setFont(new Font("Arial", 20));
				deleteBT.setStyle(
						"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
				deleteBT.setOnAction(m -> {
					if (!(locations.search(comb.getValue()))) {
						dialog(AlertType.ERROR, "Sorry, the location you are trying to update does not exist!");
					} else {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Confirmation Dialog");
						alert.setHeaderText("This location has about "
								+ locations.getNode(comb.getValue()).location.getNamesAVL().countNodes()
								+ " martyr record...");
						alert.setContentText("Are you sure you want to delete it?");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							locations.remove(comb.getValue());
							dialog(AlertType.INFORMATION, "The location have been deleted successfully.");
							System.out.println(locations.count);

						} else {
							alert.close();
						}

					}
				});
				VBox deleteVB = new VBox();
				deleteVB.setSpacing(10);
				deleteVB.getChildren().addAll(comb, deleteBT);
				deleteVB.setAlignment(Pos.CENTER);
				tab1BP.setCenter(deleteVB);
			});

			Button search1 = new Button("Search for a location");
			search1.setMaxWidth(400);
			search1.setFont(new Font("Arial", 15));
			search1.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			// action to search for a specific location then give the selected location its
			// value and shoe the other tabs
			search1.setOnAction(e -> {
				TextField tf = new TextField("insert the location you are searching for here...");
				tf.setMaxWidth(300);
				tf.setMinHeight(50);
				Button searchBT = new Button("Search");
				searchBT.setFont(new Font("Arial", 20));
				searchBT.setStyle(
						"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
				searchBT.setOnAction(m -> {
					if (!(locations.search(tf.getText()))) {
						dialog(AlertType.ERROR, "Sorry, the location you are searching for does not exist!");

					} else {
						selectedLocation = tf.getText();
						dialog(AlertType.INFORMATION,
								"The location have been founded, and it's value have been saved as selected location");
						tabPane.getTabs().addAll(tab2, tab3, tab4);
						tabPane.getSelectionModel().select(tab2);
					}
				});
				VBox searchVB = new VBox();
				searchVB.setSpacing(10);
				searchVB.getChildren().addAll(tf, searchBT);
				searchVB.setAlignment(Pos.CENTER);
				tab1BP.setCenter(searchVB);
			});

			VBox buttonVB = new VBox();
			buttonVB.setAlignment(Pos.CENTER_LEFT);
			buttonVB.setSpacing(20);
			buttonVB.getChildren().addAll(insert1, update1, delete1, search1);
			tab1BP.setLeft(buttonVB);
		}
		/////////////////////////////////////////////////////////////////////////////////
		// the 2nd tab to insert delete update search for a martyr record
		{
			BorderPane tab2BP = new BorderPane();
			tab2.setContent(tab2BP);
			backGround(tab2BP);
			Button insert2 = new Button("Insert new marty info.");
			insert2.setMaxWidth(400);
			insert2.setFont(new Font("Arial", 15));
			insert2.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			// insert action for inserting a non existing martyr record
			insert2.setOnAction(e -> {
				tab2BP.setTop(null);
				TextField nameTF = new TextField("insert the martyr name here...");
				nameTF.setMaxWidth(300);
				nameTF.setMinHeight(50);
				TextField ageTF = new TextField("insert the martyr age here...");
				ageTF.setMaxWidth(300);
				ageTF.setMinHeight(50);
				ArrayList<String> loc = new ArrayList<>();
				DoubleNode cur = locations.getfirst();
				int j = 0;
				while (j < locations.count) {
					loc.add(locations.getNode(cur.location.getLocation()).location.getLocation());
					cur = cur.next;
					j++;
				}
				ComboBox<String> comb = new ComboBox<>();
				for (int i = 0; i < loc.size(); i++) {
					comb.getItems().add(loc.get(i));
				}
				comb.setOnAction(m -> {
					for (int i = 0; i < loc.size(); i++) {
						comb.getItems().add(loc.get(i));

					}
				});
				comb.setValue(selectedLocation);
				TextField dateTF = new TextField("insert the martyr date of death here...");
				dateTF.setMaxWidth(300);
				dateTF.setMinHeight(50);
				TextField genderTF = new TextField("insert the martyr gender here...");
				genderTF.setMaxWidth(300);
				genderTF.setMinHeight(50);
				Button insertBT = new Button("Insert");
				insertBT.setFont(new Font("Arial", 20));
				insertBT.setStyle(
						"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
				insertBT.setOnAction(m -> {
					if (locations.getNode(comb.getSelectionModel().getSelectedItem().toString()).location.getNamesAVL()
							.findByName(nameTF.getText()) != null) {
						dialog(AlertType.ERROR, "Sorry, the martyr record you are trying to add already exist!");
					} else {
						Martyr martyr = new Martyr(nameTF.getText());
						if (isAge(ageTF.getText())) {
							int age = (int) Integer.parseInt(ageTF.getText());
							martyr.setAge(age);
						}
						try {
							Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateTF.getText());
							martyr.setDateOfDeath(date);
						} catch (ParseException e1) {
							dialog(AlertType.ERROR, "The date you are tring to insert is unvalid!");
						}
						if (isChar(genderTF.getText())) {
							char gender = genderTF.getText().charAt(0);
							martyr.setGender(gender);
						}
						if (martyr.getAge() != 0 && !(martyr.getDateOfDeath() == null) && martyr.getGender() != 'N') {
							locations.getNode(comb.getValue()).location.getNamesAVL().insertByName(martyr);
							if (locations.getNode(comb.getValue()).location.getDatesAVL()
									.findByDate(martyr.getDateOfDeath()) != null) {
								locations.getNode(comb.getValue()).location.getDatesAVL()
										.findByDate(martyr.getDateOfDeath()).data.getStack().push(martyr);
							} else {

								locations.getNode(comb.getValue()).location.getDatesAVL()
										.insertByDate(new DateStack(martyr.getDateOfDeath(), new Stack()));
								locations.getNode(comb.getValue()).location.getDatesAVL()
										.findByDate(martyr.getDateOfDeath()).data.getStack().push(martyr);
							}
							dialog(AlertType.INFORMATION, "The record have been added successfully.");
						} else {
							dialog(AlertType.WARNING,
									"You have to insert valid data to compleate the insert operation.");
						}

					}
					// sort the double linked list after adding a new record
				});
				VBox insertVB = new VBox();
				insertVB.setSpacing(10);
				insertVB.getChildren().addAll(nameTF, ageTF, comb, dateTF, genderTF, insertBT);
				insertVB.setAlignment(Pos.CENTER);
				tab2BP.setCenter(insertVB);
			});

			Button update2 = new Button("Update a pre-existing martyr record.");
			update2.setMaxWidth(400);
			update2.setFont(new Font("Arial", 15));
			update2.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			// an action to update an existing martyr record( name or age or location or
			// date or gender) depends on the cheack boxs
			update2.setOnAction(e -> {
				VBox updateVB = new VBox();
				Button updateBT = new Button("Update");
				updateBT.setFont(new Font("Arial", 20));
				updateBT.setStyle(
						"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
				CheckBox nameCB = new CheckBox("Update name");
				nameCB.setFont(new Font("Arial", 15));
				CheckBox ageCB = new CheckBox("Update age");
				ageCB.setFont(new Font("Arial", 15));
				CheckBox locationCB = new CheckBox("Update location");
				locationCB.setFont(new Font("Arial", 15));
				CheckBox dateCB = new CheckBox("Update date");
				dateCB.setFont(new Font("Arial", 15));
				CheckBox genderCB = new CheckBox("Update gender");
				genderCB.setFont(new Font("Arial", 15));
				TextField oldNameTF = new TextField("insert the marty name you want update his record here...");
				oldNameTF.setMaxWidth(300);
				oldNameTF.setMinHeight(40);
				TextField newNameTF = new TextField("insert the marty new name here...");
				newNameTF.setMaxWidth(300);
				newNameTF.setMinHeight(40);
				TextField ageTF = new TextField("insert the marty age here...");
				ageTF.setMaxWidth(300);
				ageTF.setMinHeight(40);
				ArrayList<String> loc = new ArrayList<>();
				DoubleNode cur = locations.getfirst();
				int j = 0;
				while (j < locations.count) {
					loc.add(locations.getNode(cur.location.getLocation()).location.getLocation());
					cur = cur.next;
					j++;
				}
				ComboBox<String> oldComb = new ComboBox<>();
				for (int i = 0; i < loc.size(); i++) {
					oldComb.getItems().add(loc.get(i));
				}
				oldComb.setValue(selectedLocation);
				oldComb.setOnAction(m -> {
					for (int i = 0; i < loc.size(); i++) {
						oldComb.getItems().add(loc.get(i));

					}
				});
				ComboBox<String> newComb = new ComboBox<>();
				for (int i = 0; i < loc.size(); i++) {
					newComb.getItems().add(loc.get(i));
				}
				newComb.setOnAction(m -> {
					for (int i = 0; i < loc.size(); i++) {
						newComb.getItems().add(loc.get(i));

					}
				});
				TextField dateTF = new TextField("insert the marty date of death here...");
				dateTF.setMaxWidth(300);
				dateTF.setMinHeight(40);
				TextField genderTF = new TextField("insert the marty gender here...");
				genderTF.setMaxWidth(300);
				genderTF.setMinHeight(40);
				HBox cbHB = new HBox();
				cbHB.getChildren().addAll(nameCB, ageCB, locationCB, dateCB, genderCB);
				cbHB.setAlignment(Pos.CENTER);
				Pane p = new Pane();
				p.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				p.getChildren().add(cbHB);
				tab2BP.setTop(p);
				nameCB.setOnAction(h -> {
					if (nameCB.isSelected()) {
						updateVB.getChildren().add(newNameTF);
						updateBT.setOnAction(m -> {
							if (locations.getNode(oldComb.getValue()).location.getNamesAVL()
									.findByName(oldNameTF.getText()) == null) {
								dialog(AlertType.ERROR,
										"Sorry, the martyr record you are trying to update does not exist!");
							} else {
								Martyr temp = locations.getNode(oldComb.getValue()).location.getNamesAVL()
										.findByName(oldNameTF.getText()).data;
								/*
								 * locations.getNode(oldComb.getValue()).location.getDatesAVL()
								 * .findByDate(temp.getDateOfDeath()).data.getStack()
								 * .findNode(temp.getName()).element.setName(newNameTF.getText());
								 */
								// locations.getNode(oldComb.getValue()).location.getDatesAVL()
								// .findByDate(locations.getNode(oldComb.getValue()).location.getNamesAVL()
								// .findByName(oldNameTF.getText()).data.getDateOfDeath()).data
								// .getStack().findNode(oldNameTF.getText()).element.setName(newNameTF.getText());
								locations.getNode(oldComb.getValue()).location.getNamesAVL()
										.findByName(oldNameTF.getText()).data.setName(newNameTF.getText());
								locations.getNode(oldComb.getValue()).location.getNamesAVL()
										.insertByName(locations.getNode(oldComb.getValue()).location.getNamesAVL()
												.delete(locations.getNode(oldComb.getValue()).location.getNamesAVL()
														.findByName(newNameTF.getText()).data).data);
								dialog(AlertType.INFORMATION, "The record have been updated successfully.");

							}
						});
					} else {
						updateVB.getChildren().removeAll(newNameTF);

					}
				});
				ageCB.setOnAction(o -> {
					if (ageCB.isSelected()) {
						updateVB.getChildren().add(ageTF);
						updateBT.setOnAction(m -> {
							if (locations.getNode(oldComb.getValue()).location.getNamesAVL()
									.findByName(oldNameTF.getText()) == null) {
								dialog(AlertType.ERROR,
										"Sorry, the martyr record you are trying to update does not exist!");
							} else {
								if (isAge(ageTF.getText())) {
									int age = (int) Integer.parseInt(ageTF.getText());
									/*
									 * locations.getNode(oldComb.getValue()).location.getDatesAVL()
									 * .findByDate(locations.getNode(oldComb.getValue()).location.getNamesAVL()
									 * .findByName(oldNameTF.getText()).data.getDateOfDeath()).data
									 * .getStack().findNode(newNameTF.getText()).element.setAge(age);
									 */
									locations.getNode(oldComb.getValue()).location.getNamesAVL()
											.findByName(oldNameTF.getText()).data.setAge(age);
									dialog(AlertType.INFORMATION, "The record have been updated successfully.");
								} else {
									dialog(AlertType.WARNING,
											"You have to insert valid age to compleate the update operation.");
								}

							}
						});

					} else {
						updateVB.getChildren().removeAll(ageTF);

					}
				});
				dateCB.setOnAction(d -> {
					if (dateCB.isSelected()) {
						updateVB.getChildren().add(dateTF);
						updateBT.setOnAction(m -> {
							if (locations.getNode(oldComb.getValue()).location.getNamesAVL()
									.findByName(oldNameTF.getText()) == null) {
								dialog(AlertType.ERROR,
										"Sorry, the martyr record you are trying to update does not exist!");
							} else {
								try {
									Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateTF.getText());
									locations.getNode(oldComb.getValue()).location.getDatesAVL()
											.findByDate(locations.getNode(oldComb.getValue()).location.getNamesAVL()
													.findByName(oldNameTF.getText()).data.getDateOfDeath()).data
											.getStack().findNode(oldNameTF.getText()).element.setDateOfDeath(date);
									/*
									 * locations.getNode(oldComb.getValue()).location.getDatesAVL()
									 * .findByDate(locations.getNode(oldComb.getValue()).location.getNamesAVL()
									 * .findByName(oldNameTF.getText()).data.getDateOfDeath()).data .getStack()
									 * .push(locations.getNode(oldComb.getValue()).location.getDatesAVL()
									 * .findByDate(locations.getNode(oldComb.getValue()).location
									 * .getNamesAVL().findByName(oldNameTF.getText()).data .getDateOfDeath()).data
									 * .getStack().removeNode(oldNameTF.getText()).element);
									 */
									locations.getNode(oldComb.getValue()).location.getNamesAVL()
											.findByName(oldNameTF.getText()).data.setDateOfDeath(date);
									// sort the linked list after updating the date
									dialog(AlertType.INFORMATION, "The record have been updated successfully.");
								} catch (ParseException e1) {
									dialog(AlertType.ERROR, "The date you are tring to insert is unvalid!");
									dialog(AlertType.WARNING,
											"You have to insert valid date to compleate the update operation.");
								}

							}
						});

					} else {
						updateVB.getChildren().removeAll(dateTF);

					}
				});

				genderCB.setOnAction(w -> {
					if (genderCB.isSelected()) {
						updateVB.getChildren().add(genderTF);
						updateBT.setOnAction(m -> {
							if (locations.getNode(oldComb.getValue()).location.getNamesAVL()
									.findByName(oldNameTF.getText()) == null) {
								dialog(AlertType.ERROR,
										"Sorry, the martyr record you are trying to update does not exist!");
							} else {
								if (isChar(genderTF.getText())) {
									char gender = genderTF.getText().charAt(0);
									Martyr temp = locations.getNode(oldComb.getValue()).location.getNamesAVL()
											.findByName(oldNameTF.getText()).data;
									// update the 2nd avl
									locations.getNode(oldComb.getValue()).location.getDatesAVL()
											.findByDate(temp.getDateOfDeath()).data.getStack()
											.findNode(temp.getName()).element.setGender(gender);
									// update the 1st avl
									locations.getNode(oldComb.getValue()).location.getNamesAVL()
											.findByName(temp.getName()).data.setGender(gender);
									dialog(AlertType.INFORMATION, "The record have been updated successfully.");
								} else {
									dialog(AlertType.WARNING,
											"You have to insert valid gender to compleate the update operation.");
								}

							}
						});
					} else {
						updateVB.getChildren().removeAll(genderTF);

					}

				});
				locationCB.setOnAction(a -> {
					if (locationCB.isSelected()) {
						updateVB.getChildren().add(newComb);
						updateBT.setOnAction(m -> {
							if (locations.getNode(oldComb.getValue()).location.getNamesAVL()
									.findByName(oldNameTF.getText()) == null) {
								dialog(AlertType.ERROR,
										"Sorry, the martyr record you are trying to update does not exist!");
							} else {
								temp = locations.getNode(oldComb.getValue()).location.getNamesAVL()
										.findByName(oldNameTF.getText()).data;
								locations.getNode(oldComb.getValue()).location.getNamesAVL().delete(temp);
								locations.getNode(newComb.getValue()).location.getNamesAVL().insertByName(temp);
								dialog(AlertType.INFORMATION, "The record have been updated successfully.");

							}
						});
					} else {
						updateVB.getChildren().removeAll(newComb);

					}

				});
				updateVB.setSpacing(10);
				updateVB.getChildren().addAll(updateBT, oldNameTF, oldComb);
				updateVB.setAlignment(Pos.CENTER);
				tab2BP.setCenter(updateVB);

			});

			Button delete2 = new Button("Delete a pre-existing martyr record.");
			delete2.setMaxWidth(400);
			delete2.setFont(new Font("Arial", 15));
			delete2.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			// action to delete an existing martyr record
			delete2.setOnAction(e -> {
				tab2BP.setTop(null);
				TextField nameTF = new TextField("insert the marty name here...");
				nameTF.setMaxWidth(300);
				nameTF.setMinHeight(50);
				ArrayList<String> loc = new ArrayList<>();
				DoubleNode cur = locations.getfirst();
				int j = 0;
				while (j < locations.count) {
					loc.add(locations.getNode(cur.location.getLocation()).location.getLocation());
					cur = cur.next;
					j++;
				}
				ComboBox<String> Comb = new ComboBox<>();
				for (int i = 0; i < loc.size(); i++) {
					Comb.getItems().add(loc.get(i));
				}
				Comb.setValue(selectedLocation);
				Comb.setOnAction(m -> {
					for (int i = 0; i < loc.size(); i++) {
						Comb.getItems().add(loc.get(i));

					}
				});
				Button deleteBT = new Button("Delete.");
				deleteBT.setFont(new Font("Arial", 20));
				deleteBT.setStyle(
						"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
				deleteBT.setOnAction(m -> {
					if ((locations.getNode(Comb.getValue()).location.getNamesAVL()
							.findByName(nameTF.getText())) != null) {
						// locations.getNode(locationTF.getText()).location.getDatesAVL()
						// .findByDate(locations.getNode(locationTF.getText()).location.getNamesAVL()
						// .findByName(nameTF.getText()).data.getDateOfDeath()).data
						// .getStack().removeNode(nameTF.getText());
						locations.getNode(Comb.getValue()).location.getNamesAVL()
								.delete(locations.getNode(Comb.getValue()).location.getNamesAVL()
										.findByName(nameTF.getText()).data);
						dialog(AlertType.INFORMATION, "The record have been deleted successfully");

					} else {
						dialog(AlertType.ERROR,
								"Sorry, the record you are trying to delete does not exist, or exists in another location");
					}
				});
				VBox deleteVB = new VBox();
				deleteVB.getChildren().addAll(nameTF, Comb, deleteBT);
				deleteVB.setSpacing(10);
				deleteVB.setAlignment(Pos.CENTER);
				tab2BP.setCenter(deleteVB);

			});
			Button search2_1 = new Button("Seacrh for martyr in the selscted location.");
			search2_1.setMaxWidth(400);
			search2_1.setFont(new Font("Arial", 15));
			search2_1.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			// action to list all the martyr names that contains a specific part
			search2_1.setOnAction(e -> {
				tab2BP.setTop(null);
				ArrayList<String> loc = new ArrayList<>();
				DoubleNode cur = locations.getfirst();
				int j = 0;
				while (j < locations.count) {
					loc.add(locations.getNode(cur.location.getLocation()).location.getLocation());
					cur = cur.next;
					j++;
				}
				ComboBox<String> Comb = new ComboBox<>();
				for (int i = 0; i < loc.size(); i++) {
					Comb.getItems().add(loc.get(i));
				}
				Comb.setValue(selectedLocation);
				Comb.setOnAction(m -> {
					for (int i = 0; i < loc.size(); i++) {
						Comb.getItems().add(loc.get(i));

					}
				});
				TextField nameTF = new TextField("Inser the name you are searching for");
				nameTF.setMaxWidth(300);
				nameTF.setMinHeight(50);
				Button searchBT = new Button("Search");
				searchBT.setStyle(
						"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
				TextArea ta = new TextArea();
				searchBT.setOnAction(m -> {
					ta.setText(locations.getNode(Comb.getValue()).location.getNamesAVL()
							.searchByPartialName(nameTF.getText(), Comb.getValue()).toString());
				});
				VBox searchVB = new VBox();
				HBox nameHB = new HBox();
				nameHB.setAlignment(Pos.CENTER);
				nameHB.setMaxWidth(600);
				nameHB.getChildren().addAll(nameTF, searchBT);
				searchVB.getChildren().addAll(Comb, nameHB, ta);
				searchVB.setSpacing(10);
				searchVB.setAlignment(Pos.CENTER);
				tab2BP.setCenter(searchVB);
			});

			VBox buttonVB = new VBox();
			buttonVB.setAlignment(Pos.CENTER_LEFT);
			buttonVB.setSpacing(20);
			buttonVB.getChildren().addAll(insert2, update2, delete2, search2_1);
			tab2BP.setLeft(buttonVB);
		}
		///////////////////////////////////////////////////////////////////////////////////
		// this tap have the statics data for the selected location

		{
			BorderPane tab3BP = new BorderPane();
			Label locationl = new Label();
			locationl.setText(selectedLocation);
			locationl.setFont(new Font("Arial", 90));
			locationl.setTextFill(Color.WHEAT);
			tab3.setContent(tab3BP);
			backGround(tab3BP);
			Button uploadBT = new Button(" Upload the summary report for the selected location");
			uploadBT.setMaxWidth(600);
			uploadBT.setFont(new Font("Arial", 15));
			uploadBT.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			uploadBT.setAlignment(Pos.CENTER);
			VBox VB = new VBox();
			VB.setSpacing(10);
			VB.setAlignment(Pos.CENTER);
			TextArea sammaryTA = new TextArea();
			sammaryTA.setMaxWidth(600);
			VB.getChildren().addAll(locationl, uploadBT, sammaryTA);
			// upload the summary in a text area
			uploadBT.setOnAction(m -> {
				sammaryTA.setFont(new Font("Arial", 15));
				sammaryTA.setBackground(
						new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				sammaryTA.appendText("The numbers of martyrs in the selected location: "
						+ locations.getNode(selectedLocation).location.getNamesAVL().countNodes() + "\n");
				sammaryTA.appendText("______________________________________________\n");
				sammaryTA.appendText("Traverse the 1st AVL level-by-level and print the Martyr’s full information: \n"
						+ locations.getNode(selectedLocation).location.getNamesAVL().printLevelOrder(selectedLocation));
				sammaryTA.appendText("______________________________________________\n");
				sammaryTA.appendText("The height of the 1st AVL tree: "
						+ locations.getNode(selectedLocation).location.getNamesAVL().height() + ". \n");
				sammaryTA.appendText("______________________________________________\n");
				sammaryTA.appendText("Traverse the 2nd AVL backward: \n" + locations.getNode(selectedLocation).location
						.getDatesAVL().printDescendingOrder(selectedLocation));
				sammaryTA.appendText("______________________________________________\n");
				sammaryTA.appendText("The date that had the maximum number of martyrs in " + selectedLocation + ": "
						+ (locations.getNode(selectedLocation).location.getDatesAVL().maxDate() + "\n"));
				sammaryTA.appendText("______________________________________________\n");
				sammaryTA.appendText("The height of the 2nd AVL tree: "
						+ locations.getNode(selectedLocation).location.getDatesAVL().height() + ". \n");
			});
			Button nextBT = new Button("Next");
			nextBT.setMaxWidth(400);
			nextBT.setFont(new Font("Arial", 15));
			nextBT.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			nextBT.setAlignment(Pos.BOTTOM_RIGHT);
			tab3BP.setRight(nextBT);
			nextBT.setOnAction(e -> {
				// move to the next location in the double linked list
				sammaryTA.setText("");
				selectedLocation = locations.getNode(selectedLocation).next.location.getLocation();
				uploadBT.setText("Upload the summary report for the selected location" + "(" + selectedLocation + ")");
				locationl.setText(selectedLocation);
			});
			Button previousBT = new Button("Previous");
			previousBT.setMaxWidth(400);
			previousBT.setFont(new Font("Arial", 15));
			previousBT.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			previousBT.setAlignment(Pos.BOTTOM_LEFT);
			tab3BP.setLeft(previousBT);
			// move to the Previous location in the double linked list
			previousBT.setOnAction(e -> {
				sammaryTA.setText("");
				selectedLocation = locations.getNode(selectedLocation).previous.location.getLocation();
				uploadBT.setText("Upload the summary report for the selected location" + "(" + selectedLocation + ")");
				locationl.setText(selectedLocation);
			});

			tab3BP.setCenter(VB);
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		// the last tab for saving the records in the same format into the file by using
		// the file chooser
		{

			BorderPane tab4BP = new BorderPane();
			tab4.setContent(tab4BP);
			backGround(tab4BP);
			Button saveBT = new Button("Choose file to save the data in it");
			saveBT.setFont(new Font("Arial", 20));
			saveBT.setStyle(
					"-fx-background-color: #000000; -fx-border-color: #ff0000; -fx-border-width: 2px;-fx-text-fill: #ffffff");
			Label title1 = new Label("ASH-SHAHEED");
			title1.setTextFill(Color.WHEAT);
			title1.setFont(new Font("Arial", 70));
			VBox saveVB = new VBox();
			saveVB.setAlignment(Pos.CENTER);
			saveVB.getChildren().addAll(title1, saveBT);
			tab4BP.setCenter(saveVB);
			saveBT.setOnAction(m -> {
				try {
					FileChooser fileChooser1 = new FileChooser();
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)",
							"*.txt");
					fileChooser1.getExtensionFilters().add(extFilter);
					File file = fileChooser1.showSaveDialog(primaryStage);
					FileWriter myWriter = new FileWriter(file);
					DoubleNode current = locations.getfirst();
					int i = 0;
					while ((i < locations.count)) {
						myWriter.append(locations.getNode(selectedLocation).location.getNamesAVL()
								.printLevelOrder(selectedLocation));
						current = current.next;
						i++;
					}
					//
					dialog(AlertType.INFORMATION, "The data had been saved successfully");
					myWriter.close();
				} catch (IOException e1) {
					dialog(AlertType.ERROR, "Erorr, could not save the data in this file");
				}
			});
		}
		tabPane.getTabs().addAll(tab1);
		Scene tabScene = new Scene(tabPane, 650, 450);
		tabsStage.setTitle("ASH-SHAHEED");
		tabsStage.setScene(tabScene);

	}

	public static void main(String[] args) {
		launch(args);
	}

//function to set a background to a pane
	public void backGround(Pane p) {
		try {
			BackgroundImage bGI = new BackgroundImage(new Image(getClass().getResourceAsStream("/Resources/background.jpeg")),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					BackgroundSize.DEFAULT);
			Background bGround = new Background(bGI);
			p.setBackground(bGround);
		} catch (Exception e) {
			dialog(AlertType.ERROR, "Sorry, there was an error while uploading the background");
		}
	}

	public boolean isAge(String s) {
		if (s == null) {
			return false;
		} else {
			try {
				int age = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				dialog(AlertType.ERROR, "The age you are tring to insert is unvalid!");
				return false;

			}
			return true;
		}

	}

	public boolean isDate(String s) {
		if (s == null) {
			return false;
		} else {
			try {
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(s);
			} catch (ParseException e) {
				return false;
			}
			return true;
		}
	}

	public boolean isChar(String s) {
		if (s == null) {
			return false;
		} else {
			try {
				char gender = s.charAt(0);
				if (gender == 'F' || gender == 'M' || gender == 'm' || gender == 'f') {
					return true;
				} else {
					dialog(AlertType.ERROR, "The gender you are tring to insert is unvalid!");
					return false;
				}
			} catch (Exception e) {
				dialog(AlertType.ERROR, "The gender you are tring to insert is unvalid!");
				return false;
			}
		}
	}

	// function to show a different types of dialogs
	public void dialog(AlertType t, String s) {
		Alert alert = new Alert(t);
		alert.setTitle("Dialog");
		alert.setHeaderText("");
		alert.setContentText(s);
		alert.showAndWait();
	}

}
