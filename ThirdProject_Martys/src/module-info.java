module ThirdProject_Martys {
		requires javafx.controls;
		requires javafx.base;
		
		opens application to javafx.graphics, javafx.fxml,javafx.base;
	    exports application;

	}