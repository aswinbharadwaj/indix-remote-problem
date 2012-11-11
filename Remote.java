import java.io.*;
import java.util.*;

public class Remote{

		static int total;  // The total number of button presses
		public static int process(int curchannel,int nextchannel,List<Integer> history,List<Integer> channels,int curpos,int endpos)
		{
				int diff,hisdiff,rawpresses;
				int rightrot,leftrot;
				int tot = 0;
				if(curchannel > nextchannel)
        		{
        			diff = channels.indexOf(curchannel) - channels.indexOf(nextchannel); // down presses required
    		    	//System.out.println("diff:"+diff);
    		    	if(history.contains(nextchannel))
    		    	{
        				hisdiff = curpos - history.lastIndexOf(nextchannel) -1;
    		    		//System.out.println("hisdiff:"+hisdiff);
    		    		diff = (hisdiff < diff)?hisdiff:diff;
    		    	}
    		    	rightrot = ((endpos - channels.indexOf(curchannel)) + (channels.indexOf(nextchannel)+1));
    		    	
    		    	if(rightrot < diff)
    		    		diff = rightrot;
    		    	rawpresses =  String.valueOf(nextchannel).length(); // the length of the channel number
    		    	//System.out.println("rawpresses:"+rawpresses);
    		    	tot = (diff < rawpresses)?diff:rawpresses;
    		    	//System.out.println(total);
        		}
        		else if(curchannel < nextchannel)
        		{
        			diff = channels.indexOf(nextchannel) - channels.indexOf(curchannel); // up presses required
        			//System.out.println("diff:"+diff);
        			if(history.contains(nextchannel))
    		    	{
        				hisdiff = curpos - history.lastIndexOf(nextchannel) -1;
        				//System.out.println("hisdiff:"+hisdiff);
    		    		diff = (hisdiff < diff)?hisdiff:diff;
    		    	}
        			leftrot = ((channels.indexOf(curchannel) + 1) + (endpos-channels.indexOf(nextchannel)));
        			if(leftrot < diff)
    		    		diff = leftrot;
        			rawpresses =  String.valueOf(nextchannel).length(); // the length of the channel number
    		    	//System.out.println("rawpresses:"+rawpresses);
    		    	tot = (diff < rawpresses)?diff:rawpresses;
    		    	//System.out.println(total);
        		}
        		else
        		{
        			// no addition to the channels
        		}
        		return tot;
		}

        public static int process2(int curchannel,int nextchannel,List<Integer> channels,int endpos)
        {
                int diff,rawpresses;
                int rightrot,leftrot;
                int tot = 0;
                if(curchannel > nextchannel)
                {
                    diff = channels.indexOf(curchannel) - channels.indexOf(nextchannel); // down presses required
                    //System.out.println("diff:"+diff);
                    rightrot = ((endpos - channels.indexOf(curchannel)) + (channels.indexOf(nextchannel)+1));
                    
                    if(rightrot < diff)
                        diff = rightrot;
                    rawpresses =  String.valueOf(nextchannel).length(); // the length of the channel number
                    //System.out.println("rawpresses:"+rawpresses);
                    tot = (diff < rawpresses)?diff:rawpresses;
                    //System.out.println(total);
                }
                else if(curchannel < nextchannel)
                {
                    diff = channels.indexOf(nextchannel) - channels.indexOf(curchannel); // up presses required
                    //System.out.println("diff:"+diff);
                    
                    leftrot = ((channels.indexOf(curchannel) + 1) + (endpos-channels.indexOf(nextchannel)));
                    if(leftrot < diff)
                        diff = leftrot;
                    rawpresses =  String.valueOf(nextchannel).length(); // the length of the channel number
                    //System.out.println("rawpresses:"+rawpresses);
                    tot = (diff < rawpresses)?diff:rawpresses;
                    //System.out.println(total);
                }
                else
                {
                    // no addition to the channels
                }
                return tot;
        }

		public static void main(String[] args) {
			
		try{
		// Getting input from file and printing output to a file
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        
        //Declaring necessary variables

        String line;
        String params[];
        int T,cs,ce,nc,bc,t = 0; // The number of test cases, start and end values of channels [inclusive] and number of needed,blocked channels
        List<Integer> channels,needed,blocked; // The channel sequence,needed and the blocked sequence
        
        int curchannel,nextchannel;
        List<Integer> history,temphistory,removablehistory;
        int min,newmin,additive,temp,size;

        //Reading the number of test cases
        line = br.readLine();
        T = Integer.parseInt(line);

        //Performing every test case
        while(t < T)
        {

        	//Reading the limits
        	line = br.readLine();
        	params = line.split(" ");
        	cs = Integer.parseInt(params[0]);
        	ce = Integer.parseInt(params[1]);
        	channels = new ArrayList<Integer>();
        	//Creating the channel sequence
        	for(int channel = cs; channel <= ce; ++channel)
        		channels.add(channel);
        	//Reading the number of blocked channels and blocked channel sequence
        	line = br.readLine();
        	params = line.split(" ");
        	//Storing the number of blocked channels
        	bc = Integer.parseInt(params[0]);
        	blocked = new ArrayList<Integer>();
        	//Reading the blocked channel list
        	for(int i = 0; i < bc; ++i)
        		blocked.add(Integer.parseInt(params[i+1]));
        	//Reading the number of needed channels and allowed channel sequence
        	line = br.readLine();
        	params = line.split(" ");
        	//Storing the number of needed channels
        	nc = Integer.parseInt(params[0]);
        	needed = new ArrayList<Integer>();
        	history = new ArrayList<Integer>();
        	//Reading the needed channel list
        	for(int i = 0; i < nc; ++i)
        		needed.add(Integer.parseInt(params[i+1]));
        	//Removing the blocked channels from the list
        	//System.out.println(channels);
        	channels.removeAll(blocked);
        	//System.out.println(channels);
        	//System.out.println(needed);
        	//System.out.println(blocked);
        	total = 0;
        	total += String.valueOf(needed.get(0)).length();
        	history.add(needed.get(0));
        	for (ListIterator<Integer> it = needed.listIterator(); it.hasNext(); ){

        	 	curchannel = it.next();
        		if(it.hasNext())
        		{
        			nextchannel = it.next();
        		}
        		else
        		{
        			// already processed
        			//System.out.println(curchannel);
        			break;
        		}
        		//System.out.println(curchannel+" "+nextchannel);
        		
        		min = process(curchannel,nextchannel,history,channels,it.previousIndex(),channels.size()-1);
        		additive = 0;
			    removablehistory = new ArrayList<Integer>(history);
        		Collections.reverse(history);
        		for (int historychannel:history)
        		{
                    additive++;            
                    size = removablehistory.size();
				    removablehistory.remove(size-1);
        			temp = process2(historychannel,nextchannel,channels,channels.size()-1);	
        			newmin = temp+additive-1;
        			min = (newmin < min)?newmin:min;

        		}
			Collections.reverse(history);
        		total += min;
        		history.add(nextchannel);
        		it.previous();
        	}
        	System.out.println(total);
        	t++;
        }
    	}
    	catch(Exception ex)
    	{ex.printStackTrace();}
      }
}
