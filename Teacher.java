//Batagianni Lamprini-Theodosia 3150115
import java.util.ArrayList;

public class Teacher {
    private String code;
    private String name;
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    private int []days = new int[5];
    private int maxweek;
    
    public int getDays(int i) {
        return days[i];
    }
    
    public int getWeek() {
        return maxweek;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setMaxDay(int maxday){
        for(int i=0; i<5; i++) {
            days[i] = maxday;
        }
    }
    
    public void setMaxWeek (int maxweek) {
        this.maxweek = maxweek;
    }
    
    public String getCode(){
        return code;
    }
    
    public String getName(){
        return name;
    }
    
    public int getMaxWeek(){
        return maxweek;
    }
    
    public ArrayList<Lesson> getLessons(){
        return lessons;
    }
    
    public void reduce(int i){
        maxweek--;
        days[i]--;
    }
    
    public void addLesson(Lesson cod){
        lessons.add(cod);
    }
}