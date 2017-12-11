import java.util.ArrayList;

public class Domain {
	
	private final String name;
	private final Domain parentDomain;
	private static final ArrayList<Domain> domainRecord = new ArrayList<Domain>();
	
	public Domain(String name, Domain parentDomain)
	{
		this.name = name;
		this.parentDomain = parentDomain;
		domainRecord.add(this);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public boolean belongsTo(String name)
	{
		if(name == this.name)
			return true;
		else if(parentDomain != null)
			return parentDomain.belongsTo(name);
		else
			return false;
			
	}
	

	public String toString()
	{
		String str = "";
		
		if(this.parentDomain != null)
			str = this.parentDomain.toString();
		
		return str + "/" + this.name;
	}
	
	public static Domain getByName(String name)
	{
		for(Domain d : domainRecord)
		{
			if(name == d.getName())
				return d;
		}
		return null;
	}
	
	public static void init()
	{
		String data[][] = {
				{"Politics",			null},
				{"Sports",				null},
				{"Entertainment",		null},
				{"Internal politics", 	"Politics"},
				{"External politics",	"Politics"},
				{"Basketball",			"Sports"},
				{"Football",			"Sports"},
				{"Celebrity news",		"Entertainment"},
				{"Listicles",			"Entertainment"}
		};
		
		for(String []pair : data)
		{
			if(pair[1] == null)
				new Domain(pair[0], null);
			else
			{
				Domain parent;
				if((parent = getByName(pair[1])) != null)
					new Domain(pair[0], parent);
			}
		}
	}
}
