public class Error_handling {
    private int parameter,number;

    private String num;
    GUI_window_display window=new GUI_window_display();

    public Object is_number(String input)
    {
        try {
            number = Integer.parseInt(input);
            return number;
        } catch (NumberFormatException e) {
            window.warning_messages("paramter not numberic");
            return "Error: Input is not a number";
        }

    }

    public int getNumber(){
        return number;
    }
}