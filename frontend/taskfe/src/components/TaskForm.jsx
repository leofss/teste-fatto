import React from "react";
import { useState, useEffect } from "react";
import TaskService from "../services/TaskService";

const TaskForm = ({ task, fetchTasks }) => {
  const [title, setTitle] = useState("");
  const [cost, setCost] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [id, setId] = useState("")
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false); 

  useEffect(() => {
    if (task) {
      setId(task.id)
      setTitle(task.title);
      setCost(task.amount);
      setDueDate(task.dueDate);
    }
  }, [task]);

  const handleError = (error) => {
    if (error.response && error.response.data && error.response.data.message) {
      setErrorMessage(error.response.data.message);
    } else {
      setErrorMessage("Currently unavailable, please try again later.");
    }
  };

  const handleAddTask = async (taskData) => {
    setErrorMessage("");
    setIsLoading(true);
    try {
      await TaskService.addTask(taskData);
      fetchTasks();
      setTitle("");
      setCost("");
      setDueDate("");
    } catch (error) {
      handleError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleEditTask = async (id, taskData) => {
    setErrorMessage("");
    setIsLoading(true);
    try {
      await TaskService.editTask(id, taskData);
      fetchTasks();
    } catch (error) {
      handleError(error);
    } finally {
      setIsLoading(false); 
    }
  };
  const handleSubmit = async (e) => {
    e.preventDefault();

    const taskData = {
      title,
      amount: parseFloat(cost), 
      dueDate,
    };

    if (!task) {
     handleAddTask(taskData)
    }else{
      console.log(taskData)
      handleEditTask(id, taskData)
    }
  };
  return (
    <form onSubmit={handleSubmit}>
      <div className="mb-4">
        <label className="block text-white font-bold mb-2">Title</label>
        <input
          type="text"
          id="title"
          name="title"
          className="border rounded w-full py-2 px-3 mb-2 text-black"
          placeholder="eg. Meeting with client"
          required
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
      </div>

      <div className="mb-4">
        <label className="block font-bold mb-2 text-white">Cost</label>
        <input
          type="number"
          id="amount"
          name="amount"
          className="border rounded w-full py-2 px-3 mb-2 text-black"
          placeholder="eg. 120.90"
          required
          value={cost}
          onChange={(e) => setCost(e.target.value)}
        />
      </div>

      <div className="mb-4">
        <label className="block text-white font-bold mb-2">Due Date</label>
        <input
          type="date"
          id="dueDate"
          name="dueDate"
          className="border rounded w-full py-2 px-3 mb-2 text-black"
          required
          value={dueDate}
          onChange={(e) => setDueDate(e.target.value)}
        />
      </div>
      <button
        className="bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
        type="submit"
      >
        {task ? "Edit task" : "Add task"}
      </button>
      {isLoading && <div>Loading, please wait...</div>}
      <div>{errorMessage}</div>
    </form>
  );
};

export default TaskForm;
