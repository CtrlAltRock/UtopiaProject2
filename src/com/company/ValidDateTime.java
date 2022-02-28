package com.company;

public class ValidDateTime {
    public static boolean isValidDate(String st)
    {
        String[] sArr = st.split("-");
        try
        {
            int[] monthArray = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
            int year = Integer.parseInt(sArr[0]);
            int month = Integer.parseInt(sArr[1]);
            int day = Integer.parseInt(sArr[2]);
            if (isLeapYear(year))
                monthArray[1] += 1;
            return year >= 1900 & day > 0 & day <= monthArray[month - 1];
        } catch (Exception e)
        {
            return false;
        }

    }

    public static boolean isValidTime(String st)
    {
        String[] sArr = st.split(":");
        try
        {
            int hour = Integer.parseInt(sArr[0]);
            int minute = Integer.parseInt(sArr[1]);
            int second = Integer.parseInt(sArr[2]);

            return hour > 0 & hour < 24 & minute > 0 & minute < 60 & second > 0 & second < 60;
        } catch (Exception e)
        {
            return false;
        }
    }

    public static boolean isLeapYear(int year)
    {
        if (year % 4 == 0) {

            // if the year is century
            if (year % 100 == 0) {

                // if year is divided by 400
                // then it is a leap year
                return year % 400 == 0;
            }

            // if the year is not century
            else
                return true;
        }

        else
            return true;
    }
}
