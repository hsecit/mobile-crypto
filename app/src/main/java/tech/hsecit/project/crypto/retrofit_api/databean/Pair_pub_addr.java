package tech.hsecit.project.crypto.retrofit_api.databean;


public class Pair_pub_addr
{
    public String pubaddrbc1_p2wpkh;

    public String pubaddrbc1_p2wsh;

    public String pubaddr3;

    public String pubkeyc;

    public String pubaddr1;

    public String pubkey;

    public String getPubaddrbc1_p2wpkh ()
    {
        return pubaddrbc1_p2wpkh;
    }

    public void setPubaddrbc1_p2wpkh (String pubaddrbc1_p2wpkh)
    {
        this.pubaddrbc1_p2wpkh = pubaddrbc1_p2wpkh;
    }

    public String getPubaddrbc1_p2wsh ()
    {
        return pubaddrbc1_p2wsh;
    }

    public void setPubaddrbc1_p2wsh (String pubaddrbc1_p2wsh)
    {
        this.pubaddrbc1_p2wsh = pubaddrbc1_p2wsh;
    }

    public String getPubaddr3 ()
    {
        return pubaddr3;
    }

    public void setPubaddr3 (String pubaddr3)
    {
        this.pubaddr3 = pubaddr3;
    }

    public String getPubkeyc ()
    {
        return pubkeyc;
    }

    public void setPubkeyc (String pubkeyc)
    {
        this.pubkeyc = pubkeyc;
    }

    public String getPubaddr1 ()
    {
        return pubaddr1;
    }

    public void setPubaddr1 (String pubaddr1)
    {
        this.pubaddr1 = pubaddr1;
    }

    public String getPubkey ()
    {
        return pubkey;
    }

    public void setPubkey (String pubkey)
    {
        this.pubkey = pubkey;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pubaddrbc1_p2wpkh = "+pubaddrbc1_p2wpkh+", pubaddrbc1_p2wsh = "+pubaddrbc1_p2wsh+", pubaddr3 = "+pubaddr3+", pubkeyc = "+pubkeyc+", pubaddr1 = "+pubaddr1+", pubkey = "+pubkey+"]";
    }
}