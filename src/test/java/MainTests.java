import Domain.Nota;
import Domain.Student;
import Domain.Teme;
import Repository.NoteRepo;
import Repository.StudentRepo;
import Repository.TemeRepo;
import Service.ServiceNote;
import Service.ServiceStudent;
import Service.ServiceTeme;
import Validator.NotaValidator;
import Validator.StudentValidator;
import Validator.TemeValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MainTests
{
    ServiceStudent serviceStudent;
    ServiceTeme serviceTeme;
    ServiceNote serviceNote;
    @Before
    public void setUp() throws Exception {
        StudentValidator studentValidator = new StudentValidator();
        String filenameStudent = "src/main/resources/studenti.xml";
        StudentRepo studentRepo = new StudentRepo(studentValidator,filenameStudent);
        serviceStudent = new ServiceStudent(studentRepo);

        TemeValidator temeValidator = new TemeValidator();
        String filenameTeme = "src/main/resources/Teme.xml";
        TemeRepo temeRepo = new TemeRepo(temeValidator,filenameTeme);
        serviceTeme= new ServiceTeme(temeRepo);

        NotaValidator notaValidator = new NotaValidator();
        NoteRepo noteRepo = new NoteRepo(notaValidator);
        serviceNote= new ServiceNote(noteRepo);
        }

    @Test
    public void testAddStudent(){
        Student student = new Student("12","Nume",936,"email@gmail.com","prof");
        Student addedStudent=this.serviceStudent.add(student);
        assertEquals(student , addedStudent);
    }

    @Test
    public void testAddTema(){
        Teme tema = new Teme(25, "descrip", 4, 7);
        assertEquals(serviceTeme.add(tema),tema);
    }

    @Test
    public void testAddNote(){
        Student student = new Student("12","Nume",936,"email@gmail.com","prof");
        Teme tema = new Teme(25, "descrip", 4, 7);
        Map.Entry<String,Integer> id= new AbstractMap.SimpleEntry<>("nota 1",9);
        Nota nota = new Nota(id,student,tema,10,5);

        assertEquals(serviceNote.add(nota,"fd"),nota);
    }

    @Test
    public void bigBang(){
        testAddStudent();
        testAddTema();
        testAddNote();
    }

    @Test
    public void integrationTestAddAssignment(){
        Student student = new Student("14","Nume",936,"email@gmail.com","prof");
        Student addedStudent=this.serviceStudent.add(student);
        Teme tema = new Teme(26, "descripion", 4, 7);
        assertEquals(student , addedStudent);
        assertEquals(serviceTeme.add(tema),tema);
    }

    @Test
    public void integrationTestAddGrade(){
        Student student = new Student("15","Numele",936,"email@gmail.com","prof");
        Student addedStudent=this.serviceStudent.add(student);
        Teme tema = new Teme(26, "descripion", 4, 7);
        Map.Entry<String,Integer> id= new AbstractMap.SimpleEntry<>("nota 1",9);
        Nota nota = new Nota(id,student,tema,10,5);

        assertEquals(serviceNote.add(nota,"fd"),nota);
        assertEquals(student , addedStudent);
        assertEquals(serviceTeme.add(tema),tema);
    }
}