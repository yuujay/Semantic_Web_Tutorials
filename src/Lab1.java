import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.*;

public class Lab1{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Added this code to remove the logging details
		org.apache.log4j.Logger.getRootLogger().
		setLevel(org.apache.log4j.Level.OFF);
		
		String name = "Keven L. Ates";
		String URI = "http://utdallas/semclass#KevenAtes";
		String DOB = "01-Jan-1901";
		String email = "atescomp@utdallas.edu";
		String title = "Senior Lecturer";
	
		// create an empty default model
		Model vCardModel = ModelFactory.createDefaultModel();
		//Create the resource
		Resource kevenAtes = vCardModel.createResource(URI);
		//Add the properties to the resource
		kevenAtes.addProperty(VCARD.TITLE,title);
		kevenAtes.addProperty(VCARD.EMAIL,email);
		kevenAtes.addProperty(VCARD.BDAY,DOB);
		kevenAtes.addProperty(VCARD.FN,name);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("Lab1_2_gumaMaheswariJaganathan.xml");
			vCardModel.write(writer, "RDF/XML-ABBREV");
			writer = new PrintWriter("Lab1_2_gumaMaheswariJaganathan.ntp");
			vCardModel.write(writer, "N-TRIPLE");
			writer = new PrintWriter("Lab1_2_gumaMaheswariJaganathan.n3");
			vCardModel.write(writer, "N3");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.close();
		
		String directory = "MyDatabases/Dataset1";
		Dataset dataset = TDBFactory.createDataset(directory) ;
		dataset.begin(ReadWrite.WRITE);
		Model rdfModel = dataset.getNamedModel("myrdf");
		InputStream fileread = FileManager.get().open("gowtham_FOAFFriends.rdf");
		rdfModel.read(fileread, "myrdf");
		rdfModel.add(vCardModel);
		
		try {
			writer = new PrintWriter("Lab1_4_gumaMaheswariJaganathan.xml");
			rdfModel.write(writer, "RDF/XML-ABBREV");
			writer = new PrintWriter("Lab1_4_gumaMaheswariJaganathan.ntp");
			rdfModel.write(writer, "N-TRIPLE");
			writer = new PrintWriter("Lab1_4_gumaMaheswariJaganathan.n3");
			rdfModel.write(writer, "N3");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.close();
		dataset.commit();
		dataset.end();
	}

}