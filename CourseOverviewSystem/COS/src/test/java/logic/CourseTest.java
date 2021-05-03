package logic;

import cos.controls.Course;
import cos.controls.Task;
import cos.controls.WorkHourCounter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CourseTest {

    private Task  testTask;
    private Course testCourse;
    private WorkHourCounter testWHC;

    @Before
    public void initialize(){
        testWHC = new WorkHourCounter();
        testWHC.setStartDate(LocalDateTime.now());
        testWHC.setEndDate(LocalDateTime.now().plusDays(55).plusMinutes(10));
        testCourse = new Course(false,testWHC,
                "test","no need","notes",0,0);

        for (int i = 0; i <10; i++) {

            testWHC = new WorkHourCounter();
            testWHC.setEndDate(LocalDateTime.now().plusDays(30-i));
            testWHC.setStartDate(LocalDateTime.now().plusDays(i));
            testTask = new Task(false, testWHC,"task"+i,""+i,"",0);
            testCourse.addTask(testTask);
        }

    }

    @Test
    public void taskIsFirst(){
        testWHC = new WorkHourCounter();
        testWHC.setStartDate(LocalDateTime.now());
        testWHC.setEndDate(LocalDateTime.now().plusDays(1));
        testTask = new Task(false,testWHC, "should be first","first","",0);
        testCourse.addTask(testTask);
        assertEquals(testTask,testCourse.getTaskList().get(0));

    }

    @Test
    public void taskIsLast(){

        testWHC = new WorkHourCounter();
        testWHC.setStartDate(LocalDateTime.now());
        testWHC.setEndDate(LocalDateTime.now().plusDays(30));
        testTask = new Task(false,testWHC, "should be last","last","",0);
        testCourse.addTask(testTask);
        assertEquals(testTask,testCourse.getTaskList().get(testCourse.getTaskList().size()-1));
    }


    @Test
    public void markTaskDone(){
        testTask = new Task(false,testWHC, "should be passed","pass","",0);
        testCourse.addTask(testTask);
        testCourse.markTaskDone(testTask);

        assertTrue(testCourse.getDoneTasks().contains(testTask));

    }

    @Test
    public void markTaskDoneOnlyOne(){
        testTask = new Task(false,testWHC, "should be passed","pass","",0);
        testCourse.addTask(testTask);
        testWHC = new WorkHourCounter(199L);
        testWHC.setStartDate(LocalDateTime.now());
        testWHC.setEndDate(LocalDateTime.now().plusDays(100));

        testTask = new Task(false,testWHC, "should be passed","pass","",0);
        testCourse.addTask(testTask);

        testCourse.markTaskDone(testTask);
        testCourse.markTaskDone(testTask);
        assertEquals(1, testCourse.getDoneTasks().size());

    }

    @Test
    public void markTaskDoneOnlyTwo(){
        testTask = new Task(false,testWHC, "should be passed","pass","",0);
        testCourse.addTask(testTask);
        testTask = new Task(false,testWHC, "should be passed","pass","",0);
        testCourse.addTask(testTask);

        testCourse.markTaskDone(testTask);
        testTask = new Task(false,testWHC, "should be passed second","pass","",0);
        testCourse.addTask(testTask);

        testCourse.markTaskDone(testTask);

        assertEquals(2, testCourse.getDoneTasks().size());

    }

    @Test
    public void removeTask(){

        testTask = new Task(false,testWHC, "should be passed","pass","",0);
        testCourse.addTask(testTask);

        testCourse.removeTask(testTask);
        assertFalse(testCourse.getTaskList().contains(testTask));
    }

    @Test
    public void stringReturn(){

        assertEquals("test - Days left: 55",testCourse.toString());

    }


    @Test
    public void removeTaskFail(){

        assertFalse(testCourse.removeTask(new Task(false,testWHC, "do not exist","pass","",0)));
    }

    @Test
    public void markDoneTaskFail(){

        assertFalse(testCourse.markTaskDone(new Task(false,testWHC, "do not exist","pass","",0)));
    }
}
