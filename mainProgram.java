//Batagianni Lamprini-Theodosia 3150115
import java.io.*;
import java.util.*;

public class mainProgram {
    
    public static void main(String args[]){
        int maxl = 0; //will be used for evenly spread writing lessons
        int maxt = 0; //will be used for evenly spread writing teachers
        FileReader[] file = new FileReader[2];
        BufferedReader[] buffer = new BufferedReader[2];
        ArrayList<Lesson> lessons = new ArrayList<Lesson>();
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        try {
            for(int i=0; i<2; i++){
                file[i] = new FileReader(args[i]);
                buffer[i] = new BufferedReader(file[i]);
            }
            String line = "";
            int i=0;
            int l=0;
            while(line != null){
                lessons.add(new Lesson());
                line = buffer[0].readLine();
                lessons.get(i).setCode(line.substring(line.indexOf(" ")+1));
                line = buffer[0].readLine();
		lessons.get(i).setLessonName(line.substring(line.indexOf(" ")+1));
		line = buffer[0].readLine();
		lessons.get(i).setClass(line.substring(line.indexOf(" ")+1));
		line = buffer[0].readLine();
		lessons.get(i).setNumberOfHours(Integer.parseInt(line.substring(line.indexOf(" ")+1)));
		lessons.get(i).upL();
		line = buffer[0].readLine();
		l = lessons.get(i).getLessonName().length();
		if (maxl<l) maxl = l; //we want max length of lessons
		i++;
            }
            buffer[0].close();
            line = "";
            i = 0;
            int t = 0;
            String lessonname;
            while(line!=null){
                teachers.add(new Teacher());
                line = buffer[1].readLine();
                teachers.get(i).setCode(line.substring(line.indexOf(" ")+1));
                line = buffer[1].readLine();
                teachers.get(i).setName(line.substring(line.indexOf(" ")+1));
                line = buffer[1].readLine();
                teachers.get(i).setMaxDay(Integer.parseInt(line.substring(line.indexOf(" ")+1)));
                line = buffer[1].readLine();
                teachers.get(i).setMaxWeek(Integer.parseInt(line.substring(line.indexOf(" ")+1)));
                line = buffer[1].readLine();
                String line2 = line.substring(line.indexOf(" ")+1);
                while(line2 != null){
                    if(line2.indexOf(",") >= 0) lessonname = line2.substring(0,line2.indexOf(","));
                    else lessonname = line2;
                    for(Lesson les:lessons){
                        if(les.getLessonName().equals(lessonname)){
                            teachers.get(i).addLesson(les);
                            break;
                        }
                    }
                    if(line2.indexOf(",") >= 0) line2 = line2.substring(line2.indexOf(",")+1);
                    else line2=null;
                }
                line = buffer[1].readLine();
                t = teachers.get(i).getName().length();
		if (maxt<t) maxt = t; //we want max length of teachers
                i++;
            }
            buffer[1].close();
        }
        catch(IOException e) {
            System.out.println("Error during opening files");
        }
        
        State state = new State(lessons, teachers);
        while(!state.isTerminal()){
            state = state.getBestChild();
        }
	Lesson [][][] classpr = state.getLessons();
        Teacher [][][] teacherpr = state.getTeachers();
	String day[] = new String[5];
	day[0] = "Monday";
	day[1] = "Tuesday";
	day[2] = "Wednesday";
	day[3] = "Thursday";
	day[4] = "Friday";
	String sector[] = new String[9];
	sector[0] = "A1";
	sector[1] = "A2";
	sector[2] = "A3";
	sector[3] = "B1";
	sector[4] = "B2";
	sector[5] = "B3";
	sector[6] = "C1";
	sector[7] = "C2";
	sector[8] = "C3";
        try{
            FileWriter fw = new FileWriter("Schedule.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Program of classes\n");
            int yt;
            String wr;
            for (int x=0; x<5; x++){
		bw.write ("\n" + day[x] + "\n");
		for (int y=0; y<7; y++){
                    yt = y+1;
                    bw.write("" +yt+ ". ");
                    for (int z=0; z<9; z++){
			if (classpr[x][y][z]!=null && classpr[x][y][z].getLessonName()!=null){
                            bw.write(sector[z]+ ": " +classpr[x][y][z].getLessonName() + "-" + teacherpr[x][y][z].getName());
                            wr = classpr[x][y][z].getLessonName() + teacherpr[x][y][z].getName();
			}
			else {
                            bw.write(sector[z]+": "+"FREE TIME");
                            wr = "FREE TIME";
			}
			for (int u=0; u<=(maxl+maxt-wr.length()); u++) bw.write(" "); //evely spread writing
                    }
                    bw.write("\n");
		}
            }
            bw.write("\n");
            bw.close();
        }
        catch(IOException e){
            System.out.println("Error during writing in file");
	}
    }
    
}