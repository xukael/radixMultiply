import java.util.*;

public class Multiply {

    public static String NUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        Multiply m = new Multiply();
        m.run();
    }

    public void run() {
        Scanner scn = new Scanner(System.in);

        // 횟수 입력
        int N = scn.nextInt();

        int[] radixArr = new int[N];
        String[] AArr = new String[N];
        String[] BArr = new String[N];

        // data input
        for(int i=0; i < N; i++) {
            radixArr[i] = scn.nextInt();
            AArr[i] = scn.next();
            BArr[i] = scn.next();
        }

        // print output
        for(int i=0; i < N; i++) {
            System.out.println(calcMultiply(radixArr[i], AArr[i], BArr[i]));
        }

        scn.close();
    }

    String calcMultiply(int radix, String A, String B) {
        String num1;
        String num2;
        boolean isAMinus = false;
        boolean isBMinus = false;

        // 음수인지 확인하고 -기호 제거
        if (A.startsWith("-")) {
            isAMinus = true;
            A = A.substring(1);
        }
        if (B.startsWith("-")) {
            isBMinus = true;
            B = B.substring(1);
        }

        // num1에 길이가 긴 수, num2에 길이가 작은 수를 할당하고 string을 역순으로 배열
        if (B.length() > A.length()) {
            num1 = reverseString(B);
            num2 = reverseString(A);
        } else {
            num1 = reverseString(A);
            num2 = reverseString(B);
        }

        // num1, num2 각 자리수별 곱셈연산
        int[] result1 = new int[num1.length()+num2.length()+1];

        for(int num2Idx=0 ; num2Idx < num2.length(); num2Idx++) {
            for(int num1Idx=0; num1Idx < num1.length(); num1Idx++) {
                result1[num1Idx+num2Idx] += toDecimal(radix, num1.charAt(num1Idx)) * toDecimal(radix, num2.charAt(num2Idx));
            }
        }

        // normalize
        for (int i=0; i<result1.length-1; i++) {
            // result1[i]를 radix로 나눈 몫과 나머지를 구한다.
            int q = result1[i] / radix;
            int r = result1[i] % radix;
            result1[i] = r;
            result1[i+1] += q;
        }

        //string으로 변환
        String result = "";
        for(int i=0; i<result1.length; i++) {
            result = String.valueOf(fromDecimal(radix, result1[i])) + result;
        }

        result = removeHeadingZeros(result);

        //음수면 - 기호 추가
        if (((isAMinus && !isBMinus) || (isBMinus && !isAMinus)) && !result.equals("0")) {
            result = "-"+result;
        }

        return result;
    }

    // radix진수에서 10진수로 변환
    int toDecimal(int radix, char N) {
        return Multiply.NUMERIC.indexOf(N);
    }

    // 10진수에서 radix진수로 변환
    char fromDecimal(int radix, int N) {
        return Multiply.NUMERIC.charAt(N);
    }

    // String을 역순으로 반환
    String reverseString(String orig) {
        char[] arr = orig.toCharArray();
        String result="";
        for(int i=0; i<arr.length; i++) {
            result = String.valueOf(arr[i]) + result;
        }
        return result;
    }

    // String에서 앞에 붙은 연속된 0를 제거
    String removeHeadingZeros(String orig) {
        String result = orig;
        for(int i=0; i<orig.length()-1; i++) {
            if (result.charAt(0) == '0') {
                result = result.substring(1);
            } else  {
                break;
            }
        }
        return result;
    }
}