package lts;

public interface IEventManager
{
	void removeClient(EventClient client);
	void addClient(EventClient client);
	void post(LTSEvent event);
}
