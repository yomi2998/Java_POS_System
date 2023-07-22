package java_pos_system;

import java.util.Scanner;

public class Receiver {

    public int getInt(String prompt, int minval, int maxval) {
        int i = -999;
        while (i == -999 || i <= minval || i > maxval) {
            try {
                Scanner s = new Scanner(System.in);
                System.out.print(prompt);
                i = s.nextInt();
                if (i <= minval || i > maxval) {
                    throw new Exception();
                }
            } catch (Exception e) {
                if (i <= minval) {
                    System.out.println("Invalid input, value must be over " + minval + ".");
                } else if (i > maxval) {
                    System.out.println("Invalid input, vlaue must be lower or equal to " + maxval + ".");
                } else {
                    System.out.println("Invalid input, please try again.");
                }
            }
        }
        return i;
    }

    public double getDouble(String prompt, double minval, double maxval) {
        double d = -999.0;
        while (d == -999.0 || d <= minval || d > maxval) {
            try {
                Scanner s = new Scanner(System.in);
                System.out.print(prompt);
                d = s.nextDouble();
                if (d <= minval || d > maxval) {
                    throw new Exception();
                }
            } catch (Exception e) {
                if (d <= minval) {
                    System.out.println("Invalid input, value must be over " + minval + ".");
                } else if (d > maxval) {
                    System.out.println("Invalid input, vlaue must be lower or equal to " + maxval + ".");
                } else {
                    System.out.println("Invalid input, please try again.");
                }
            }
        }
        return d;
    }

    public boolean getYN(String prompt) {
        char c = '-';
        while (c != 'Y' && c != 'N') {
            try {
                Scanner s = new Scanner(System.in);
                System.out.print(prompt);
                c = Character.toUpperCase(s.next().charAt(0));
                if (c != 'Y' && c != 'N') {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again.");
            }
        }
        return c == 'Y';
    }

    public char getChar(String prompt, String constraint, boolean exclusion) {
        char c;
        while (true) {
            try {
                Scanner s = new Scanner(System.in);
                System.out.print(prompt);
                c = s.next().charAt(0);
                if (exclusion) {
                    for (char chk : constraint.toCharArray()) {
                        if (chk == c) {
                            throw new Exception();
                        }
                    }
                    return c;
                } else {
                    for (char chk : constraint.toCharArray()) {
                        if (chk == c) {
                            return c;
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again.");
            }
        }
    }

    public String getStr(String prompt, int minlen, int maxlen) {
        int entry = 0;
        String str = "-";
        while (entry == 0 || str.length() > maxlen || str.length() <= minlen) {
            ++entry;
            try {
                Scanner s = new Scanner(System.in);
                System.out.print(prompt);
                str = s.nextLine();
                if (str.length() > maxlen || str.length() <= minlen) {
                    throw new Exception();
                }
            } catch (Exception e) {
                if (str.length() <= minlen) {
                    System.out.println("Invalid input, minimum character must be over " + minlen + ".");
                } else if (str.length() > maxlen) {
                    System.out.println("Invalid input, minimum character must be less or equal to " + maxlen + ".");
                }
            }
        }
        return str;
    }

    public Date getDate(String prompt) {
        Date doi = new Date();
        while (true) {
            Scanner s = new Scanner(System.in);
            try {
                System.out.print(prompt);
                String dateStr = s.nextLine();
                String[] splitedDate = dateStr.split("/");
                doi.day = Integer.parseInt(splitedDate[0]);
                doi.month = Integer.parseInt(splitedDate[1]);
                doi.year = Integer.parseInt(splitedDate[2]);
                if (doi.year < 1900 || doi.year > 2023) {
                    throw new Exception();
                }
                if (doi.month < 1 || doi.month > 12) {
                    throw new Exception();
                }
                if (doi.day < 1 || doi.day > 31) {
                    throw new Exception();
                }
                if ((doi.month == 4 || doi.month == 6 || doi.month == 9 || doi.month == 11) && doi.day > 30) {
                    throw new Exception();
                }
                if (doi.month == 2) {
                    boolean isLeapYear = ((doi.year % 4 == 0 && doi.year % 100 != 0) || doi.year % 400 == 0);
                    if (isLeapYear && doi.day > 29) {
                        throw new Exception();
                    }
                    if (!isLeapYear && doi.day > 28) {
                        throw new Exception();
                    }
                }
                return doi;
            } catch (Exception e) {
                System.out.println("Invalid date, please try again.");
            }
        }
    }
}
