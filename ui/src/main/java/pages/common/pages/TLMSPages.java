package pages.common.pages;

import lombok.Getter;
import pages.BasePage;
import pages.admin_dashboard.AdminDashboardPage;
import pages.auth.LoginPage;

import java.lang.reflect.Field;

@Getter
public class TLMSPages extends BasePage {
    protected LoginPage loginPage;
    protected AdminDashboardPage adminDashboardPage;

    public TLMSPages(){
        initializeFields();
    }
    private void initializeFields(){
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields){
            try{
                field.setAccessible(true);
                if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())){
                    Class<?> fieldType = field.getType();
                    Object fieldValue = fieldType.getDeclaredConstructor().newInstance();
                    field.set(this, fieldValue);
                }
            } catch (Exception e ){
                e.printStackTrace();
            }
        }
    }
}
