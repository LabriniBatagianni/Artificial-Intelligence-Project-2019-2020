//Batagianni Lamprini-Theodosia 3150115
import java.util.ArrayList;

public class State {
    private ArrayList<Lesson> lessons;
    private ArrayList<Teacher> teachers;
    private Lesson[][][] namelessons = new Lesson[5][7][9];//5 days 7 hours 9 classes
    private Teacher[][][] nameteachers = new Teacher[5][7][9];
    private int x=0,y=0,z=0;
    
    public State(ArrayList<Lesson> lessons, ArrayList<Teacher> teachers){
        this.teachers = teachers;
        this.lessons = lessons;
    }
    
    public Lesson[][][] getLessons() {
        return namelessons;
    }
    
    public Teacher[][][] getTeachers() {
        return nameteachers;
    }
    
    public Teacher findTeacher(){
        ArrayList<Lesson> who;
	for (Teacher t:teachers){
            who = t.getLessons();
            for (Lesson l:who){
		if ((l.getLessonName()).equals(namelessons[x][y][z].getLessonName())){
                    return t;
		}
            }
        }
        return null;
    }
    
    public boolean isEvenlySpreadTeachers(){ //evenly spread hours for all teachers
	int m;
	for (int j=0; j<5; j++){
            for (int i=1; i<teachers.size(); i++){
                m = teachers.get(i).getDays(j);
                if (m != teachers.get(i-1).getDays(j)) return false;
            }
        }
        return true;
    }
    
    public boolean evenlySpreadDays(){ //evenly spread hours in all days
        for (int z1=0; z1<z; z1++){
            int sum[] = new int[5];
            for (int x1=0; x1<x; x1++){
                sum[x1] = 0;
                for (int y1=0; y1<y; y1++){
                    if (namelessons[x1][y1][z1] != null) sum[x1]++;
                }
                if (x1>=1) {
                    if(sum[x1]!=sum[x1-1]) return false;
                }
            }
            
        }
        return true;
    }
    
    public boolean isTiredTeacher(){ //makes sure the teacher doesn't teach more than two continuous hours
        boolean b1 = false, b2 = false;
	if (y>1){
            for (int w=0; w<=z; w++){
                if (nameteachers[x][y-1][w] != null) {
                    if (nameteachers[x][y][z].getName().equals(nameteachers[x][y-1][w].getName())) b1=true;
                }
                if (nameteachers[x][y-2][w] != null) {
                    if (nameteachers[x][y][z].getName().equals(nameteachers[x][y-2][w].getName())) b2=true;
                }
                if (b1&&b2) break;
            }
        }
        return (b1&&b2);
    }
    
    public int priority(){
        int pr=0;
        if (!isTiredTeacher()) pr++;
	if (evenlySpreadDays()) pr++;
	if (isEvenlySpreadTeachers()) pr++;
        if (namelessons[x][y][z].Active()) pr++;
	return pr;
    }
    
    public boolean isAtTheSameTime(){ //checks if a teacher teaches in a specific day and hour more than one classes
	if (nameteachers[x][y][z] == null) return false;
	for (int i=0; i<z; i++){
            if (nameteachers[x][y][i] == null) continue;
            if (nameteachers[x][y][z].getName().equals(nameteachers[x][y][i].getName())) return true;
	}
	return false;
    }
    
    public String c(){
        if (z >= 6) return "C";
        else if (z >= 3) return "B";
        else return "A";
    }
    
    public int max(int x, int y){
        if (x>y) return x;
	else return y;
    }
    
    public boolean write(Lesson l, boolean real){
        if (l == null || l.getLessonName() == null) return false;
        namelessons[x][y][z] = l;
        nameteachers[x][y][z] = findTeacher();
        if (isAtTheSameTime()||!namelessons[x][y][z].getClassABC().equals(c())||namelessons[x][y][z].getAvailableHours()==0||nameteachers[x][y][z].getDays(x)==0||nameteachers[x][y][z].getWeek()==0){
            delete();
            return false;
        }
        if (real){
            namelessons[x][y][z].reduce();
            nameteachers[x][y][z].reduce(x);
            goNext();
        }
        return true;
    }
    
    public State getBestChild(){ //returns the best child of the current state
        int size = lessons.size();
        State states[] = new State[size];
        int MAX = 0;
        int MAXN = 0;
        boolean written = false;
            for(int i=0; i<size; i++){
                states[i] = this;
                if (!this.write(lessons.get(i), false)) continue;
                int pr = states[i].priority();
                written = true;
                if(i == 0) MAX = pr;
                else MAX = max(MAX,pr);
                if (MAX == pr) MAXN = i;
                this.delete();
                if(pr == 4) break;
            }
            if(!written){
                goNext();
                return this;
            }
            State newState = this;
            newState.write(lessons.get(MAXN), true);
            return newState;
    }
       
    public void goNext(){ //changes the values of x, y, z
        if(x==4 && y==6 && z!=8){
            x=0;
            y=0;
            z++;
            for(Lesson LessonA:lessons) {
            	LessonA.upL();
            }    		
        }
        else if(y==6){
            for(int i=0; i<7; i++) {
        	if(namelessons[x][i][z] != null)
        	namelessons[x][i][z].activate();
            }
            y=0;
            x++;		
        }
        else y++;
    }
    
    public void delete(){
        namelessons[x][y][z] = null;
        nameteachers[x][y][z] = null;	
    }
    
    public boolean isTerminal(){ //checks if the program has ended, meaning x,y,z are at maximum prices
        if (x!=4||y!=6||z!=8) return false;
	return true;
    }
}