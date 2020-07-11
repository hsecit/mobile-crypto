package tech.hsecit.project.crypto.retrofit_api.databean;


public class Private_key
{
    public String wif;

    public String wifc;

    public String hex;

    public String getWif ()
    {
        return wif;
    }

    public void setWif (String wif)
    {
        this.wif = wif;
    }

    public String getWifc ()
    {
        return wifc;
    }

    public void setWifc (String wifc)
    {
        this.wifc = wifc;
    }

    public String getHex ()
    {
        return hex;
    }

    public void setHex (String hex)
    {
        this.hex = hex;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [wif = "+wif+", wifc = "+wifc+", hex = "+hex+"]";
    }
}