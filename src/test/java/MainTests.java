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
import Validator.ValidationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MainTests
{
    ServiceStudent serviceStudent;
    ServiceTeme serviceTeme;
    ServiceNote serviceNote;

    @Rule
    public ExpectedException exceptionPolicy = ExpectedException.none();

    @Before
    public void setUp() {
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
    public void testAddStudentSuccess() {
        System.out.println("Add Student Fail: Null ID");
        Student student = new Student("12","Nume",936,"email@gmail.com","prof");
        Student addedStudent = this.serviceStudent.add(student);
        assertEquals(student, addedStudent);
    }

    @Test
    public void testAddStudentFailureNullId() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nID invalid");
        System.out.println("Add Student Fail: Null ID");
        Student student = new Student(null,"Nume",936,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureEmptyId() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nID invalid");
        System.out.println("Add Student Fail: Empty ID");
        Student student = new Student("","Nume",936,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureNonNumericId() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nID invalid");
        System.out.println("Add Student Fail: Non-numeric ID");
        Student student = new Student("asd345","Nume",936,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureGroupBelow111() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nGrupa invalida");
        System.out.println("Add Student Fail: Group Below 111");
        Student student = new Student("12","Nume",0,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureGroupAbove937() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nGrupa invalida");
        System.out.println("Add Student Fail: Group Above 937");
        Student student = new Student("12","Nume",2000,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureGroupYear0() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nGrupa invalida");
        System.out.println("Add Student Fail: Group Year 0");
        Student student = new Student("12","Nume",906,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureGroupYear4() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nGrupa invalida");
        System.out.println("Add Student Fail: Group Year 4");
        Student student = new Student("12","Nume",946,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureGroupNumber0() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nGrupa invalida");
        System.out.println("Add Student Fail: Group Number 0");
        Student student = new Student("12","Nume",930,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureGroupNumber8() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nGrupa invalida");
        System.out.println("Add Student Fail: Group Number 8");
        Student student = new Student("12","Nume",938,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureEmailNoAt() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nEmail invalid");
        System.out.println("Add Student Fail: E-mail address without @");
        Student student = new Student("12","Nume",936,"email.gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureEmailNoDot() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nEmail invalid");
        System.out.println("Add Student Fail: E-mail address without .");
        Student student = new Student("12","Nume",936,"email@gmail@com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureInvalidName() {
        exceptionPolicy.expect(ValidationException.class);
        exceptionPolicy.expectMessage("\nNume invalid");
        System.out.println("Add Student Fail: Invalid Name");
        Student student = new Student("12","Nume123",936,"email@gmail.com","prof");
        this.serviceStudent.add(student);
        
    }

    @Test
    public void testAddStudentFailureInvalidProfessor() {
        exceptionPolicy.expect(ValidationException.class);
        System.out.println("Add Student Fail: Invalid Professor");
        exceptionPolicy.expectMessage("\nNume profesor invalid");
        Student student = new Student("12","Nume123",936,"email@gmail.com","prof432");
        this.serviceStudent.add(student);
        
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
    public void integrationTestAddAssignment(){
        Student student = new Student("14","Nume",936,"email@gmail.com","prof");
        Student addedStudent = this.serviceStudent.add(student);
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

    @Test
    public void runAllTests() {
        testAddStudentSuccess();
        testAddStudentFailureNullId();
        testAddStudentFailureNonNumericId();
        testAddStudentFailureEmptyId();
        testAddStudentFailureGroupBelow111();
        testAddStudentFailureGroupAbove937();
        testAddStudentFailureGroupYear0();
        testAddStudentFailureGroupYear4();
        testAddStudentFailureGroupNumber0();
        testAddStudentFailureGroupNumber8();
        testAddStudentFailureEmailNoAt();
        testAddStudentFailureEmailNoDot();
        testAddStudentFailureInvalidName();
        testAddStudentFailureInvalidProfessor();
        testAddTema();
        testAddNote();
        integrationTestAddAssignment();
        integrationTestAddGrade();
    }
}