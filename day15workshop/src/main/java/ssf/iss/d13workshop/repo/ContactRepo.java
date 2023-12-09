package ssf.iss.d13workshop.repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;

import ssf.iss.d13workshop.model.Contact;

@Repository
public class ContactRepo {

    // final String dirPath = System.getProperty("user.dir") + "/data";
    //user.dir is current directory of project
    final String dirPath = "data";
    // if path start with /, will start from computer root. otherwise will continue from current path 
    // "/Users/CelineNg/Desktop/d13/d13workshop/data";

    private List<Contact> contacts = new LinkedList<>();
    // private Map<String, List<Contact>> idMap = new HashMap<>();

    public void save(Contact contact) throws FileNotFoundException {
    
        contacts.add(contact); 

        String hexAlphabet = RandomStringUtils.randomAlphabetic(4);
        String hexNum = RandomStringUtils.randomNumeric(4);
        StringBuilder sb = new StringBuilder();
        String fileName = sb.append(hexAlphabet).append(hexNum).toString();
        contact.setId(fileName);

        File f = new File(dirPath + "/" + fileName);

        OutputStream os = new FileOutputStream(f, true);
        PrintWriter pw = new PrintWriter(os);
        pw.println("ID: " + contact.getId().toString());
        pw.println("Name: " + contact.getName().toString());
        pw.println("Email: " + contact.getEmail().toString());
        pw.println("Phone No: " + contact.getPhoneNo().toString());
        pw.println("Date of Birth: " + contact.getDoB().toString());
        pw.flush();
        pw.close();
    }

    public Contact openFileId (String id){
        String goPath = dirPath + "/" + id;
        Path filePath = Paths.get(goPath);
        boolean fileExists = Files.exists(filePath); 
        System.out.println(goPath);
        System.out.println(filePath);
        Contact contact = new Contact();

        if(fileExists){
            try{
                FileReader fr = new FileReader(goPath);
                BufferedReader br = new BufferedReader(fr);
                String line;
                // System.out.println("==========hereeee");
                while ((line=br.readLine()) !=null){
                    String[] terms = line.split(": ");
                    if ("ID".equals(terms[0])) {contact.setId(terms[1]);} 
                    if ("Name".equals(terms[0])) {contact.setName(terms[1]);} 
                    if ("Email".equals(terms[0])){contact.setEmail(terms[1]);} 
                    if ("Phone No".equals(terms[0])){contact.setPhoneNo(terms[1]);}
                    if ("Date of Birth".equals(terms[0])){contact.setDoB(LocalDate.parse(terms[1]));}
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!fileExists) {contact =null;}
        return contact;
    }

    public List<Contact> listContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Path.of(dirPath))) {
            for (Path filePath : directoryStream) {
                String fn = filePath.getFileName().toFile().getName();
                if (fn.equals(".DS_Store")){continue; }
                System.out.println(fn);

                Contact newCon = openFileId(fn);
                contacts.add(newCon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;   
    }

    

}