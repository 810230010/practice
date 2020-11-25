package com.huajia.daily.callback;

/*
 * @description:
 * @author: huajia
 * @create: 2020-11-20 10:41
 **/
public class Teacher implements Callback{
    public void ask(Student student) {
        System.out.println("老师提问");
        student.answer(this);
    }

    @Override
    public void callback(String answer) {
        System.out.println("收到学生的答案:"+answer);
    }
}
