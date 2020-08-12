//package Model;
//
//import java.util.Random;
//
//public class init {
//
//
//    public static void main(String[] args) {
//        Random rand = new Random();
//        for (Student student : Student.getStudentArrayList()) {
//            for (Exam exam : Exam.getExamArrayList()) {
//                boolean[] isRa = new boolean[Subject.getSubjectArrayList().size()];// not repeating the result
//                int subsnum = Subject.getSubjectArrayList().size();
//                for (int i = 0; i < (int) (rand.nextDouble() * subsnum/2 + subsnum/2); i++) {
//                    isRa[rand.nextInt(subsnum)] = true;
//                }
//                for (int i = 0; i < subsnum; i++) {
//                    if (isRa[i]) {
//                        new Result((int) (rand.nextDouble() * 50 + 50), Subject.getSubjectArrayList().get(i).getID(),
//                                exam.getID(), student.getStudentID());
//                    }
//                }
//            }
//        }
//
//    }
//}
