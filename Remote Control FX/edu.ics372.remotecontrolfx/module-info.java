module edu.ics372.remotecontrolfx {
	requires javafx.controls;

	opens application to javafx.graphics, javafx.fxml;
}
