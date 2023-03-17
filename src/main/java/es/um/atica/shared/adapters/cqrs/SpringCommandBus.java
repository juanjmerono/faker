package es.um.atica.shared.adapters.cqrs;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import es.um.atica.shared.domain.cqrs.Command;
import es.um.atica.shared.domain.cqrs.CommandBus;
import es.um.atica.shared.domain.cqrs.CommandHandler;
import es.um.atica.shared.domain.cqrs.SyncCommand;
import es.um.atica.shared.domain.cqrs.SyncCommandBus;
import es.um.atica.shared.domain.cqrs.SyncCommandHandler;

@Component
@Primary
public class SpringCommandBus implements CommandBus,SyncCommandBus {

    private Map<Class, CommandHandler> handlers;
    private Map<Class, SyncCommandHandler> syncHandlers;
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public SpringCommandBus(List<CommandHandler> commandHandlerImplementations, List<SyncCommandHandler> syncCommandHandlerImplementations) {
        this.handlers = new HashMap<>();
        commandHandlerImplementations.forEach(commandHandler -> {
            Class<?> commandClass = getCommandClass(commandHandler);
            handlers.put(commandClass, commandHandler);
        });
        this.syncHandlers = new HashMap<>();
        syncCommandHandlerImplementations.forEach(syncCommandHandler -> {
            Class<?> commandClass = getCommandClass(syncCommandHandler);
            syncHandlers.put(commandClass, syncCommandHandler);
        });
    }


    @Override
    public void handle(Command command) throws Exception {
        if (!handlers.containsKey(command.getClass())) {
            throw new Exception(String.format("No handler for %s", command.getClass().getName()));
        }
        executor.submit(()->{
            handlers.get(command.getClass()).handle(command);
        });
    }

    @Override
    public <T> T handle(SyncCommand<T> command) throws Exception {
        if (!syncHandlers.containsKey(command.getClass())) {
            throw new Exception(String.format("No handler for %s", command.getClass().getName()));
        }
        return (T) syncHandlers.get(command.getClass()).handle(command);
    }

    private Class<?> getCommandClass(CommandHandler handler) {
        Type commandInterface = ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        return getClass(commandInterface.getTypeName());
    }

    private Class<?> getCommandClass(SyncCommandHandler synchandler) {
        Type commandInterface = ((ParameterizedType) synchandler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
        return getClass(commandInterface.getTypeName());
    }

    private Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            return null;
        }
    }


}