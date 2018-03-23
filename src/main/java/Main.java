import Entity.Rates;
import db.DB;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by admin on 22.03.2018.
 */
public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Please type u choose");
            System.out.println("1) Add new balance");
            System.out.println("2) Add new rates");
            System.out.println("3) Show value rates by this code");
            System.out.println("4) Show all rates value");
            System.out.println("5) Exit");

            int choose = in.nextInt();
            Main.chooseMenu(choose);
        }
    }

    private static void chooseMenu(int choose){
        Scanner in = new Scanner(System.in);
        DB db = new DB();
        switch (choose){
            case 1:
                System.out.println("Type name of balance");
                String nameValue = in.nextLine();
                System.out.println("Type value");
                int value = in.nextInt();
                db.addBalanceValue(nameValue,value);
                break;
            case 2:
                System.out.println("Type code of rates");
                String code = in.nextLine();
                System.out.println("Type formul");
                String formul = in.nextLine();
                db.addRates(code,formul);
                break;
            case 3:
                System.out.println("Type code of rates");
                code = in.nextLine();
                Rates rate = db.getRatesByCode(code);
                System.out.println(rate.getCode()+" = "+ Main.calculeteFormuls(rate));
                break;
            case 4:
                List<Rates> rates = db.readAllFormuls();
                for (Rates r:rates) {
                    System.out.println(r.getCode()+" = "+ Main.calculeteFormuls(r));
                }
                break;
            case 5: System.exit(0);
            default:
                System.out.println("You missclick. Retype u choose");
                Main.chooseMenu(in.nextInt());break;

        }
    }

    public static float calculeteFormuls(Rates rate){
            DB db = new DB();
            String formula = rate.getFormul();
            char[] parseForm = formula.toCharArray();
            ArrayList<String> resultMas = new ArrayList<>();
            StringBuilder val = new StringBuilder();
            for(int i=0;i<parseForm.length;i++){
                if(parseForm[i]=='+'  || parseForm[i]=='-'  || parseForm[i]=='*'  || parseForm[i]=='/' || parseForm[i]=='('  || parseForm[i]==')' ){
                    resultMas.add(String.valueOf(val));
                    val= new StringBuilder();
                    resultMas.add(String.valueOf(parseForm[i]));
                }
                else {
                    val.append(parseForm[i]);
                }
            }
            resultMas.add(String.valueOf(val));
            List<String> form = new ArrayList<>();
            for (String sumbol: resultMas) {
                if(sumbol.equals("+") || sumbol.equals("-") || sumbol.equals("/") || sumbol.equals("*") || sumbol.equals("(") || sumbol.equals(")")){
                    form.add(sumbol);
                }
                if(sumbol.contains("rate")){
                    float number = Main.getRate(sumbol);
                    form.add(String.valueOf(number));
                }
                else {
                    int value = db.findValue(sumbol);
                    if(value==-1){
                    }
                    else{
                        form.add(String.valueOf(value));
                    }

                }
            }
            StringBuilder resultFormulaThisNumber = new StringBuilder();
            for (String s:form) {
                resultFormulaThisNumber.append(s);
            }

            float res = Calculator.main(String.valueOf(resultFormulaThisNumber));
            return res;
        }

    public static float getRate(String str){
        DB db = new DB();
        String formula = db.readFormul(str);
        char[] parseForm = formula.toCharArray();
        ArrayList<String> resultMas = new ArrayList<>();
        StringBuilder val = new StringBuilder();
        for(int i=0;i<parseForm.length;i++){
            if(parseForm[i]=='+'  || parseForm[i]=='-'  || parseForm[i]=='*'  || parseForm[i]=='/' || parseForm[i]=='('  || parseForm[i]==')' ){
                resultMas.add(String.valueOf(val));
                val= new StringBuilder();
                resultMas.add(String.valueOf(parseForm[i]));
            }
            else {
                val.append(parseForm[i]);
            }
        }
        resultMas.add(String.valueOf(val));
        List<String> form = new ArrayList<>();
        for (String sumbol: resultMas) {
            if(sumbol.equals("+") || sumbol.equals("-") || sumbol.equals("/") || sumbol.equals("*") || sumbol.equals("(") || sumbol.equals(")")){
                form.add(sumbol);
            }
            if(sumbol.contains("rate")){
                Main.getRate(sumbol);
            }
            else {
                int value = db.findValue(sumbol);
                if(value==-1){
                }
                else{
                    form.add(String.valueOf(value));
                }

            }
        }
        StringBuilder resultFormulaThisNumber = new StringBuilder();
        for (String s:form) {
            resultFormulaThisNumber.append(s);
        }

        float res =  Calculator.main(String.valueOf(resultFormulaThisNumber));
        return res;
    }
}