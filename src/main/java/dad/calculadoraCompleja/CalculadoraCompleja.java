package dad.calculadoraCompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application{
	private Complejo numerador = new Complejo();
	private Complejo denominador = new Complejo();
	private Complejo resultado = new Complejo();
	private ComboBox<String> comboBox; 			
	private TextField numReal;			
	private TextField numImag;  	
	private TextField denomReal;			
	private TextField denomImag;	
	private TextField resultadoReal;			
	private TextField resultadoImaginario;		
	
	private String[] arrayTipoOperacion  = {"+","-","*","/"};
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		comboBox = new ComboBox<String>();
		comboBox.getItems().addAll(arrayTipoOperacion);
		comboBox.setOnAction(e -> realizarOperacion());
		
		
		VBox vBoxContenedor = new VBox();
		vBoxContenedor.getChildren().add(comboBox);
		vBoxContenedor.setAlignment(Pos.CENTER);
		
		numReal = new TextField();
		numReal.setPrefColumnCount(5);
		numReal.setMaxWidth(100);
		numReal.setAlignment(Pos.CENTER);
		
		numImag = new TextField();
		numImag.setPrefColumnCount(5);
		numImag.setMaxWidth(100);
		numImag.setAlignment(Pos.CENTER);
		
		HBox hBoxNumerador = new HBox();
		hBoxNumerador.setSpacing(5);
		hBoxNumerador.getChildren().addAll(numReal, new Label("+"), numImag, new Label("i")); 
		
		Bindings.bindBidirectional(numReal.textProperty(), numerador.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(numImag.textProperty(), numerador.imaginarioProperty(), new NumberStringConverter());
		
		denomReal = new TextField();
		denomReal.setPrefColumnCount(5);
		denomReal.setMaxWidth(100);
		denomReal.setAlignment(Pos.CENTER);
		
		denomImag = new TextField();
		denomImag.setPrefColumnCount(5);
		denomImag.setMaxWidth(100);
		denomImag.setAlignment(Pos.CENTER);
		
		HBox hBoxDenominador = new HBox();
		hBoxDenominador.setSpacing(5);
		hBoxDenominador.getChildren().addAll(denomReal, new Label("+"), denomImag, new Label("i")); 
		
		Bindings.bindBidirectional(denomReal.textProperty(), denominador.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(denomImag.textProperty(), denominador.imaginarioProperty(), new NumberStringConverter());
		resultadoReal = new TextField();
		resultadoReal.setPrefColumnCount(5);
		resultadoReal.setMaxWidth(100);
		resultadoReal.setAlignment(Pos.CENTER);
		resultadoReal.setDisable(true); 
		
		resultadoImaginario = new TextField();
		resultadoImaginario.setPrefColumnCount(5);
		resultadoImaginario.setMaxWidth(100);
		resultadoImaginario.setAlignment(Pos.CENTER);
		resultadoImaginario.setDisable(true); 
		
		
		HBox hBoxSolucion = new HBox();
		hBoxSolucion.setSpacing(5);
		hBoxSolucion.getChildren().addAll(resultadoReal, new Label("+"), resultadoImaginario, new Label("i")); 
		
		Bindings.bindBidirectional(resultadoReal.textProperty(), resultado.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultadoImaginario.textProperty(), resultado.imaginarioProperty(), new NumberStringConverter());
		
		VBox vBoxDatosIntroducidos = new VBox();
		vBoxDatosIntroducidos.setAlignment(Pos.CENTER);
		vBoxDatosIntroducidos.getChildren().addAll(hBoxNumerador, hBoxDenominador, hBoxSolucion);
		
		HBox root = new HBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(vBoxContenedor, vBoxDatosIntroducidos);
		Scene scene = new Scene(root, 320, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setResizable(false);
		primaryStage.show();	
	}
	
	private void realizarOperacion() {
		String usandoComboBox = comboBox.getSelectionModel().getSelectedItem(); 
		
		if(usandoComboBox == "+") {
			resultado.realProperty()
			.bind(numerador.realProperty().add(denominador.realProperty())); 
			resultado.imaginarioProperty()
			.bind(numerador.imaginarioProperty().add(denominador.imaginarioProperty())); 
		}
		else if(usandoComboBox == "-") {
			resultado.realProperty().bind(numerador.realProperty().subtract(denominador.realProperty())); 
			resultado.imaginarioProperty().bind(numerador.imaginarioProperty().subtract(denominador.imaginarioProperty())); 
		}
		else if(usandoComboBox == "*") {
			resultado.realProperty().bind(numerador.realProperty().multiply(denominador.realProperty())
					 .subtract(numerador.imaginarioProperty().multiply(denominador.imaginarioProperty())));
			resultado.imaginarioProperty().bind(numerador.realProperty().multiply(denominador.imaginarioProperty())
					 .add(numerador.imaginarioProperty().multiply(denominador.realProperty())));
		}
		else if(usandoComboBox == "/") {
			resultado.realProperty().bind(
					(numerador.realProperty().multiply(denominador.realProperty()).add(numerador.imaginarioProperty().multiply(denominador.imaginarioProperty())))
					.divide(
					(denominador.realProperty().multiply(denominador.realProperty()).add(denominador.imaginarioProperty().multiply(denominador.imaginarioProperty())))));
			resultado.imaginarioProperty().bind(
					(numerador.imaginarioProperty().multiply(denominador.realProperty()).subtract(numerador.realProperty().multiply(denominador.imaginarioProperty())))
					.divide(
					(denominador.realProperty().multiply(denominador.realProperty()).add(denominador.imaginarioProperty().multiply(denominador.imaginarioProperty()))))
			);	
		}	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
