import Task from "./Task";
import { useState, useEffect } from "react";
import TaskForm from "./TaskForm";
import TaskService from "./../services/TaskService";
import Modal from "./Modal";
import { CirclePlus } from "lucide-react";

const TaskList = () => {
  const [tasks, setTasks] = useState([]);
  const [editTask, setEditTask] = useState();
  const [quantity, setQuantity] = useState(0);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [taskIdToDelete, setTaskIdToDelete] = useState(null);

  const fetchTasks = async () => {
    setLoading(true);
    try {
      const response = await TaskService.getAllTasks();
      setTasks(response.data.content);
      setQuantity(response.data.page.totalElements);
    } catch (error) {
      console.error("Failed to load tasks:", error);
      setError("Failed to load tasks. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  const handleDelete = async (taskId) => {
    try {
      await TaskService.deleteTask(taskId);
      setTasks(tasks.filter((task) => task.id !== taskId));
      setQuantity(quantity - 1);
    } catch (error) {
      console.error("Failed to delete task:", error);
      setError("Failed to delete task. Please try again.");
    }
  };

  const confirmDelete = () => {
    if (taskIdToDelete) {
      handleDelete(taskIdToDelete);
      setShowDeleteModal(false);
      setTaskIdToDelete(null); 
    }
  };

  const moveTaskUp = (index) => {
    if (index > 0) {
      const newTasks = [...tasks];
      [newTasks[index], newTasks[index - 1]] = [newTasks[index - 1], newTasks[index]];
      setTasks(newTasks);
    }
  };

  const moveTaskDown = (index) => {
    if (index < tasks.length - 1) {
      const newTasks = [...tasks];
      [newTasks[index], newTasks[index + 1]] = [newTasks[index + 1], newTasks[index]];
      setTasks(newTasks);
    }
  };

  return (
    <div className="flex flex-col m-4">
      <table className="p-6 rounded-xl bg-slate-300">
        <thead>
          <tr>
            <th>Title</th>
            <th>Amount</th>
            <th>Due Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {loading ? (
            <tr>
              <td colSpan="4" className="notice-message">
                Loading tasks...
              </td>
            </tr>
          ) : error ? (
            <tr>
              <td colSpan="4" className="notice-message">
                {error}
              </td>
            </tr>
          ) : quantity > 0 ? (
            tasks.map((task, index) => (
              <tr key={task.id}>
                <Task
                  task={task}
                  onDelete={() => {
                    setTaskIdToDelete(task.id);
                    setShowDeleteModal(true);
                  }}
                  onEdit={() => {
                    setEditTask(task);
                    setShowModal(true);
                  }}
                  onMoveUp={() => moveTaskUp(index)}
                  onMoveDown={() => moveTaskDown(index)}
                  disableMoveUp={index === 0}
                  disableMoveDown={index === tasks.length - 1}
                />
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4" className="notice-message">
                Comece a inserir suas tarefas
              </td>
            </tr>
          )}
        </tbody>
      </table>
      <div className="text-center mt-4">
        <button
          onClick={() => {
            setShowModal(true);
            setEditTask();
          }}
        >
          <CirclePlus />
        </button>
      </div>

      {showModal && (
        <Modal onClose={() => setShowModal(false)}>
          <TaskForm task={editTask} fetchTasks={fetchTasks} />
        </Modal>
      )}

      {showDeleteModal && (
        <Modal onClose={() => setShowDeleteModal(false)}>
          <div className="flex flex-col gap-4">
            <h2 className="text-2xl">
              Are you sure you want to delete this task?
            </h2>
            <div className="flex justify-center gap-4 text-2xl">
              <button onClick={confirmDelete}>Yes</button>
              <button onClick={() => setShowDeleteModal(false)}>No</button>
            </div>
          </div>
        </Modal>
      )}
    </div>
  );
};

export default TaskList;
