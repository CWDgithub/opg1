import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {

    /*
    FIRSTVT(E)={+,*,(,i}
    FIRSTVT(T)={*,(,I}
    FIRSTVT(F)={(,I}
    LASTVT(E)={+,*,),I}
    LASTVT(T)={*,),I}
    LASTVT(F)={),I}

      + * ( ) I #
    + > < < > < >
    * > > < > < >
    ( < < < = < ?
    ) > >   >   >
    I > >   >   >
    # < < < ? < =
     */
    public static int stop=0;
    public static char[] stack=new char[1024];
    public static int[][] matrix=new int[][]
            {{1,-1,-1,1,-1,1},{1,1,-1,1,-1,1},{-1,-1,-1,0,-1,-2},{1,1,-2,1,-2,1},{1,1,-2,1,-2,1},{-1,-1,-1,-2,-1,0}};

    public static int getType(char ch){
        switch (ch){
            case '+':return 0;
            case '*':return 1;
            case '(':return 2;
            case ')':return 3;
            case 'i':return 4;
            case '#':return 5;
            default :return -1;
        }
    }

    public static boolean simply(){
        if(stop<=1){
            return false;
        }
        else if(stack[stop-1]=='i'){
            stack[stop-1]='E';
            System.out.println("R");
        }
        else if(stack[stop-1]==')'){
            if(stop<=3||stack[stop-2]!='E'||stack[stop-3]!='('){
                return false;
            }
            else {
                System.out.println("R");
                stack[stop-3]='E';
                stop-=2;
            }
        }
        else if(stack[stop-1]=='E'&&stop>2){
            if(stop>3&&stack[stop-3]=='E'&&(stack[stop-2]=='*'||stack[stop-2]=='+')){
                stack[stop-3]='E';
                System.out.println("R");
                stop-=2;
            }
            else {
                return false;
            }
        }
        else if(stack[stop-1]=='E'&&stop==2){
            System.out.println("R");
        }
        else return false;
        return true;
    }

    public static void main(String[] args){
        try {
            int i=0;
            FileReader fileReader=new FileReader(args[0]);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String string=bufferedReader.readLine();
            stack[stop++]='#';
            while (i<string.length()){
                char ch=string.charAt(i);
                /*
                if(ch=='\r'||ch=='\n'){
                    break;
                }
                */
                if(stop==1){
                    stack[stop++]=ch;
                    System.out.println("I"+ch);
                    i++;
                }
                else {
                    int t = stop - 1;
                    while (t >= 1) {
                        if (stack[t] == 'E') {
                            t--;
                        }
                        else break;
                    }
                    //find the topest teminal character
                    char left = stack[t], right = ch;
                    int lnum = getType(left), rnum = getType(right);
                    if (lnum == -1 || rnum == -1 || matrix[lnum][rnum] == -2) {
                        System.out.println("E");
                        return;
                    }
                    if (matrix[lnum][rnum] == -1) {
                        stack[stop++] = right;
                        System.out.println("I" + ch);
                        i++;
                    }
                    else if(matrix[lnum][rnum]==0){
                        stack[stop++]=right;
                        System.out.println("I"+ch);
                        boolean res = simply();
                        if (!res) {
                            System.out.println("RE");
                            return;
                        }
                        else i++;
                    }
                    else {
                        boolean res = simply();
                        if (!res) {
                            System.out.println("RE");
                            return;
                        }
                    }
                }
            }
            while (stop>2){
                boolean res = simply();
                if (!res) {
                    System.out.println("RE");
                    return;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
