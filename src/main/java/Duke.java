import duke.command.Command;
import duke.command.EndCommand;
import duke.exception.DukeException;
import duke.parser.DataParser;
import duke.parser.DateParser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Main Class of the Application, which requires the workings of all packages.
 */
public class Duke {
    /**
     * Represents the reader and the writer for the output text file.
     */
    private Storage storage;

    /**
     * Represents the list of tasks stored in the application.
     */
    private TaskList taskList;

    /**
     * Represents the interface which handles user input and interactions.
     */
    private Ui ui;

    /**
     * Parses out the user input for ui recognition.
     */
    private DataParser parser;

    /**
     * Parses out the date to a readable format for the ui.
     */
    private DateParser dateHelper;

    /**
     * Represents if the boolean has terminated or not.
     */
    protected boolean hasTerminated;

    public static void main(String[] args) {
        new Duke().run();
    }

    /**
     * Creates a new Duke class which contains all packages to process user input and file input.
     * If no file is found, a new task list is created instead.
     * If a file is found, data is retrieved from the file.
     */
    public Duke() {
        ui = new Ui();
        parser = new DataParser();
        dateHelper = new DateParser();
        hasTerminated = false;
        storage = new Storage("data/tasks.txt");
        try {
            taskList = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    /**
     * Main Logic of the Code, which runs based on user input given by the parsers.
     * Continues to create and execute commands till there is no more user input left.
     */
    public void run() {
        ui.sendGreeting();
        while (!hasTerminated) {
            if (!parser.hasAnymoreData()) {
                break;
            }

            try {
                parser.readInput();
                Command c = parser.findTaskCommand();
                hasTerminated = c.checkIfExit();
                c.execute(taskList, ui, storage, parser, dateHelper);
            } catch (AssertionError e) {
                ui.sendErrorMessage(e);
            } catch (DukeException error) {
                ui.sendErrorMessage(error);
            }
        }
        ui.sendFarewell();;
    }

    /**
     * Checks if the storage file is present in the storage or not.
     * @return false if the storage file is absent.
     */
    protected boolean isStoragePresent() {
        return storage.hasStorage();
    }

    /**
     *  Takes in a input and returns a response which duke says.
     * @param input the input received from the user.
     * @return a response in the form of a String.
     */
    protected String getResponse(String input) {
        if (hasTerminated) {
            return ".";
        }
        ui.reset();
        try {
            parser.readInput(input);
            Command c = parser.findTaskCommand();
            if (c instanceof EndCommand) {
                hasTerminated = true;
            }
            c.execute(taskList, ui, storage, parser, dateHelper);
            return ui.print();
        } catch (AssertionError e) {
            ui.sendErrorMessage(e);
            return ui.print();
        } catch (DukeException error) {
            ui.sendErrorMessage(error);
            return ui.print();
        }
    }
}
