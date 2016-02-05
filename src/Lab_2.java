import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;

public class Lab_2 {
	public static void main(String[] args){
		org.apache.log4j.Logger.getRootLogger().
		setLevel(org.apache.log4j.Level.OFF);
		
		String nameURI = "http://utdallas/semclass/person#StanleyKubrick";
		String personURI = "http://utdallas/semclass/person";
		String movieURI = "http://utdallas/semclass/movie";
		String bookURI = "http://utdallas/semclass/book";
		
		String movieClkOrangeURI = "http://utdallas/semclass/movie#AClockworkOrange";
		String movieStrLovURI = "http://utdallas/semclass/movie#Dr.Strangelove";
		
		String bookRedAlertURI = "http://utdallas/semclass/book#RedAlertBook";
		String bookOrgBookURI = "http://utdallas/semclass/book#AClockworkOrangeBook";
		
		String namePeterURI = "http://utdallas/semclass/person#PeterGeorge";
		String nameAnthonyURI = "http://utdallas/semclass/person#AnthonyBurgess";
		
		Model model = ModelFactory.createDefaultModel();
		
		Resource person = model.createResource(personURI).addProperty(RDF.type, RDFS.Class);
		Resource movie = model.createResource(movieURI).addProperty(RDF.type, RDFS.Class);
		Resource book = model.createResource(bookURI).addProperty(RDF.type, RDFS.Class);
		Resource stanleykubrik = model.createResource(nameURI);
		
		Resource AClockworkOrange = model.createResource(movieClkOrangeURI);
		Resource DrStrangelove = model.createResource(movieStrLovURI);
		
		Resource RedAlertBook = model.createResource(bookRedAlertURI);
		Resource AClockworkOrangeBook = model.createResource(bookOrgBookURI);
		
		Resource PeterGeorge = model.createResource(namePeterURI);
		Resource AnthonyBurgess = model.createResource(nameAnthonyURI);
		
		stanleykubrik.addProperty(VCARD.NAME,"Stanley Kubrick");
		stanleykubrik.addProperty(VCARD.EMAIL,"Stanley.kubrick@gmail.com");
		stanleykubrik.addProperty(VCARD.BDAY,"01/01/1945");
		stanleykubrik.addProperty(RDF.type,person);
		
		PeterGeorge.addProperty(VCARD.NAME,"Peter George");
		PeterGeorge.addProperty(VCARD.EMAIL,"peter.george@gmail.com");
		PeterGeorge.addProperty(VCARD.BDAY,"01/01/1925");
		PeterGeorge.addProperty(RDF.type,person);
		
		AnthonyBurgess.addProperty(VCARD.NAME,"Anthony Burgess");
		AnthonyBurgess.addProperty(VCARD.EMAIL,"anthony.burgess@gmail.com");
		AnthonyBurgess.addProperty(VCARD.BDAY,"02/02/1920");
		AnthonyBurgess.addProperty(RDF.type,person);
		
		Property director = model.createProperty("http://utdallas/semclass/movie#director");
		Property title = model.createProperty("http://utdallas/semclass/movie#title");
		Property year = model.createProperty("http://utdallas/semclass/movie#year");
		Property basedOn = model.createProperty("http://utdallas/semclass/movie#basedOn");
		
		AClockworkOrange.addProperty(director,stanleykubrik);
		AClockworkOrange.addProperty(title,"A Clockwork Orange");
		AClockworkOrange.addProperty(year,"1971");
		AClockworkOrange.addProperty(basedOn,AClockworkOrangeBook);
		AClockworkOrange.addProperty(RDF.type, movie);
		
		DrStrangelove.addProperty(director,stanleykubrik);
		DrStrangelove.addProperty(title,"Dr.Strangelove");
		DrStrangelove.addProperty(year,"1964");
		DrStrangelove.addProperty(basedOn, RedAlertBook);
		DrStrangelove.addProperty(RDF.type, movie);
		
		RedAlertBook.addProperty(DC.date,"01/01/1958");
		RedAlertBook.addProperty(DC.title,"Red Alert Book");
		RedAlertBook.addProperty(DC.language,"English");
		RedAlertBook.addProperty(DC.creator,PeterGeorge);
		RedAlertBook.addProperty(RDF.type, book);
		
		AClockworkOrangeBook.addProperty(DC.date,"01/01/1962");
		AClockworkOrangeBook.addProperty(DC.title,"A Clockwork Orange");
		AClockworkOrangeBook.addProperty(DC.language,"English");
		AClockworkOrangeBook.addProperty(DC.creator,AnthonyBurgess);
		AClockworkOrangeBook.addProperty(RDF.type, book);
		
		String directory = "Mydatabases/Dataset1";
		Dataset dataset = TDBFactory.createDataset(directory) ;
		dataset.begin(ReadWrite.WRITE);
		Model modelRDF=dataset.getNamedModel("myrdf");
		modelRDF.add(model);
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("Lab2_3_GUmaMaheswariJaganathan.xml");
			modelRDF.write(writer, "RDF/XML");
			writer = new PrintWriter("Lab2_3_GUmaMaheswariJaganathan.n3");
			modelRDF.write(writer, "N3");
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			writer.close();
			dataset.commit();
			dataset.end();
		}
	}
}
